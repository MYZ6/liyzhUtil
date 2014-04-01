CREATE OR REPLACE PROCEDURE "HYDW"."SYNC_PRODUCTPLAN2"() LANGUAGE SQL SPECIFIC SQL140315154937100
    BEGIN
        -- #######################################################################
        -- # 生产计划的同步规则如下：
        -- 如果一个月的一个牌号有下面的数据，第一个版本从1至27号(更确切地说，是上月的最新版本），第二个版本从7号到27号，第三个版本从12号至27号，
        -- 则从第一个版本取1-7,从第二个版本取8至12，从第三个版本取13-27，即除第一个版本，后续版本从第二条记录开始取
        -- ######################################################################
        DECLARE
            SQLCODE INT;
DECLARE
    LASTPOS INT;
DECLARE
    COMDAY INT;
DECLARE
    V_PRO_DAT VARCHAR(40);
DECLARE
    V_PLAN_KEY BIGINT;
SET V_PLAN_KEY = 55;
DELETE
FROM
    T_PM_PRODUCTION_PLAN P
WHERE
    P.ID >50;
FOR P AS CUR1
CURSOR FOR
    SELECT
        PRO_MON,
        PRO_COD
    FROM
        HYDW.SC_MOMS_MV_MAINDAYPLAN_HA P
        --        WHERE
        --            PRO_MON=DATE('2014-01-01')
        --        AND PRO_COD='02105'
    GROUP BY
        PRO_MON,
        PRO_COD
    ORDER BY
        PRO_MON DESC DO SET LASTPOS = 31;
-- 从一个月的最后一天开始往前遍历
BEGIN
    DECLARE
        V_VER_NUM DECIMAL(8,3);
DECLARE
    V_COM_DAT TIMESTAMP;
DECLARE
    CUR2
    CURSOR WITH hold FOR
        SELECT
            VER_NUM,
            COM_DAT
        FROM
            HYDW.SC_MOMS_MV_MAINDAYPLAN_HA
        WHERE
            PRO_MON=P.PRO_MON
        AND PRO_COD=P.PRO_COD
        GROUP BY
            VER_NUM,
            COM_DAT
        ORDER BY
            VER_NUM DESC;
--按版本号倒序排列
OPEN CUR2;
LOOP2:
LOOP
    FETCH
        CUR2
    INTO
        V_VER_NUM,
        V_COM_DAT;
IF (SQLCODE = 0) THEN --定义循环结束条件，避免死循环
    SET COMDAY = 1;
--如果是上月的版本，全部取完；如果是当月修改的版本，从大到小，取到修改日期为止
-- 编制日期
IF V_COM_DAT > P.PRO_MON THEN
    SET COMDAY = DAY( V_COM_DAT);
END IF ;

BEGIN
    DECLARE
        V_DAY DECIMAL(8,3);
DECLARE
    V_NAME VARCHAR(50);
DECLARE
    V_PLA_PRO DECIMAL(8,3);
DECLARE
    CUR3
    CURSOR FOR
        SELECT
            PRO_DAT,
            PRO_NAM,
            PLA_PRO
        FROM
            HYDW.SC_MOMS_MV_MAINDAYPLAN_HA
        WHERE
            PRO_MON=P.PRO_MON
        AND PRO_COD=P.PRO_COD
        AND VER_NUM = V_VER_NUM
        ORDER BY
            PRO_DAT DESC;
--按生产日期倒序排列，从最大的开始取起
OPEN CUR3;
LOOP3:
LOOP
    FETCH
        CUR3
    INTO
        V_DAY,
        V_NAME,
        V_PLA_PRO;
IF (SQLCODE = 0) THEN
    -- 假如检索到了数据，插入 debug 表.
    IF V_DAY >= COMDAY THEN
        IF V_DAY <= LASTPOS THEN --把下一个版本的第一条记录作为最大记录            
IF V_DAY >9 THEN
    SET V_PRO_DAT = LEFT(P.PRO_MON,7) ||'-'|| INT(V_DAY);
ELSE
    SET V_PRO_DAT = LEFT(P.PRO_MON,7) ||'-0'|| INT(V_DAY);
END IF;
INSERT
INTO
    T_PM_PRODUCTION_PLAN
    (
        ID,
        PRO_DAT,
        PRO_COD,
        ver_num,
        PRO_NAM,
        PLA_PRO,
        UNI_COD,
        UNI_NAM,
        REMARK1,
        REMARK2,
        REMARK3
    )
    VALUES
    (
        V_PLAN_KEY,
        DATE(V_PRO_DAT),
        '042'||P.PRO_COD,
        V_VER_NUM,
        V_NAME,
        V_PLA_PRO,
        '',
        '',
        '',
        '',
        ''
    );
SET V_PLAN_KEY = V_PLAN_KEY + 1;
END IF ;
ELSE
    SET LASTPOS = V_DAY;
LEAVE LOOP3;
END IF;
ELSE
    -- 假如没有数据，跳出循环.
    SET LASTPOS = COMDAY - 1;
-- 前一个版本只取比当前编制日期小的数据
LEAVE LOOP3;
END IF;
END LOOP;
-- 关闭游标
CLOSE CUR3;
END;
IF V_COM_DAT < P.PRO_MON THEN -- 如果是在上个月制定的版本，则不再继续往上推（即只取上个月的最新版本）
    LEAVE LOOP2;
END IF;
ELSE
    LEAVE LOOP2;
END IF;
END LOOP;
-- 关闭游标
CLOSE CUR2;
END;
END FOR;
END 