function getColumn(editor) {
	return [ {
		label : "序号",
		field : "rownumber"
	}, <#rt>
	<#list colLst as col>
	{<#lt>
		label : "${col.name}",
		field : "${col.lcamel}"
	}<#rt>
	<#if col_index != (colLst?size -1)>, </#if><#t>
	</#list>
	];
}

