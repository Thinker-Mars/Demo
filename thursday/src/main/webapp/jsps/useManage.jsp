<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/easyui.css" charset='utf-8'>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/icon.css" charset='utf-8'>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js" charset='utf-8'></script>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easyui.min.js" charset='utf-8'></script>
    	<script type='text/javascript' src="${pageContext.request.contextPath}/resources/js/easyui-lang-zh_CN.js" charset='utf-8'></script>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/date.format.js" charset='utf-8'></script>
	    <title>Start Page</title>
	    <script type="text/javascript">
	    
	    	<!--删除用户 -->
	    	function deleteUser() {
	    		var selectedRows = $("#dg").datagrid("getSelections");//选中多条记录
	    		if (selectedRows.length == 0) {
	    			$.messager.alert("系统提示", "请选择要删除的数据！");
	    			return;
	    		}
	    		var strIds = [];
	    		for (var i = 0; i < selectedRows.length; i++) {
	    			strIds.push(selectedRows[i].areaId);
	    			
	    		}
	    		var ids = strIds.join(",");//将strids中的项用逗号分割
	    		$.messager.confirm("系统提示","你确定要删除这<font color=red>" + 
	    				selectedRows.length + "</font>条数据吗", function(r) {
	    					if (r) {
	    						$.post("${pageContext.request.contextPath}/list/delete",{
	    							ids: ids
	    						}, function (result) {
	    							if (result.flag) {
	    								$.messager.alert("系统提示", "数据已成功删除！");
	    								$("#dg").datagrid("reload");
	    							} else {
	    								$.messager.alert("系统提示", "数据删除失败！");
	    							}
	    						}, "json");
	    					}
	    				});
	    		
	    	}
	    	
	    	function save() {
	    		
	    		$("#fm").form("submit", {
	    			url: "${pageContext.request.contextPath}/list/save",
	    			onSubmit: function(param) {
	    				//取值来自于form表单
	    				if ($("#priority").val == "" || $("#areaName").combobox('getValue') == "") {
	    					$.messager.alert("系统提示", "请填写完整！");
	    					return false;
	    				}
	    				
	    				param.priority = $("#priority").val();
	    				param.areaName = ($("#areaName").combobox('getValue'));
	    				return $(this).form("validate");//表单校验通过则提交
	    			},
	    			success: function(result) {
	    				var result = eval('(' + result + ')');
	    				if (result.flag) {
	    					$.messager.alert("系统提示", "添加成功！");
	    					resetData();
	    					$("#dlg").dialog("close");
	    					$("#dg").datagrid("reload");
	    				} else {
	    					$.messager.alert("系统提示", "添加失败！");
	    					return;
	    				}
	    			}
	    		})
	    	}
	    	
	    	function searchUser() {
	    		
	    		$("#dg").datagrid('load', {
					"areaName" : $("#s_areaName").val()
				});
	    		
	    	}
	    	
	    	function openAreaAddDialog() {
	    		
	    		$("#dlg").dialog("open").dialog("setTitle", "添加区域信息");
	    	}
	    	
	    	function closeUserDialog() {
	    		
	    		$("#dlg").dialog("close");
	    		resetData();
	    	}
	    	
	    	function delCloseUserDialog() {
	    		
	    		$("#dedlg").dialog("close");
	    	}
	    	
	    	
	    	function resetData() {
	    		
	    		$("#priority").val("");
	    		$("#areaName").combobox("setValue", "");
	    	}
	    	
	    	function openUserModifyDialog() {
	    		var selectedRows = $("#dg").datagrid("getSelections");
	    		if (selectedRows.length != 1) {
	    			$.messager.alert("系统提示", "请选择一条要编辑的数据！");
	    			return;
	    		}
	    		var row = selectedRows[0];
	    		$("#dedlg").dialog("open").dialog('setTitle', "修改区域信息");
	    		$("#delfm").form("load", row);//将选中的数据填充至form中
	    	}
	    	
	    	function edit() {
	    		
	    		$("#delfm").form("submit", {
	    			url: "${pageContext.request.contextPath}/list/edit",
	    			onSubmit: function(param) {
	    				
	    				if ($("#editPriority").val == "" || $("#editAreaName").combobox('getValue') == "") {
	    					$.messager.alert("系统提示", "请填写完整！");
	    					return false;
	    				}
	    				var row = $('#dg').datagrid('getSelected');//选中一条记录
	    				param.priority = $("#editPriority").val();
	    				param.areaName = encodeURI($("#editAreaName").combobox('getValue'));
	    				param.areaId = row.areaId;
	    				return $(this).form("validate");//表单校验
	    			},
	    			success: function(result) {
	    				var result = eval('(' + result + ')');
	    				if (result.flag) {
	    					$.messager.alert("系统提示", "修改成功！");
	    					resetData();
	    					$("#dedlg").dialog("close");
	    					$("#dg").datagrid("reload");
	    				} else {
	    					$.messager.alert("系统提示", "修改失败！");
	    					return;
	    				}
	    			}
	    		})
	    	}

	    	

	    </script>
	</head>

	<body style="margin: 1px">
		<table id="dg" title="用户管理" class="easyui-datagrid">
			<script>
			$("#dg").datagrid({
				url:'${pageContext.request.contextPath}/list/listarea',
				title:"用户管理",
				fitColumns:true,
				pagination:true,
				rownumbers:true,
				toolbar:'#tb',
				columns:[[
					{field:'cb',title:'',checkbox:true,align:'center'},
					{field:'areaId',title:'编号',width:50,align:'center'},
					{field:'areaName',title:'名称',width:50,align:'center'},
					{field:'priority',title:'权重',width:50,align:'center'},
					{field:'creatTime',title:'创建时间',width:50,align:'center',
						formatter: function (val, row) {
						
							if (val == null) {
				    			return '';
				    		}
				    		var mat = 'Y-m-d H:i:s';
				    		return new Date(val.time).format(mat);
						}
						
					},
					{field:'lastEditTime',title:'修改时间',width:50,align:'center',
						formatter: function (val, row) {
						
							if (val == null) {
				    			return '';
				    		}
							var mat = 'Y-m-d H:i:s';
				    		return new Date(val.time).format(mat);

						}
					
					}
				]]
			})

			</script>
		 </table>
		 
		 
		<div id="tb">
			<a href="javascript:openAreaAddDialog()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openUserModifyDialog()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteUser()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
			<div>
				 名称： <input type="text" id="s_areaName" size="20"
					οnkeydοwn="if(event.keyCode == 13)searchUser()" /> <a
					href="javascript:searchUser()" class="easyui-linkbutton"
					iconCls="icon-search" plain="true">查询</a>
			</div>
			<div id="dlg-buttons">
				<a href="javascript:save()" class="easyui-linkbutton"
					iconCls="icon-ok">保存</a> <a href="javascript:closeUserDialog()"
					class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
			</div>
			
			<div id="dlg-delbuttons">
				<a href="javascript:edit()" class="easyui-linkbutton"
					iconCls="icon-ok">保存</a> <a href="javascript:delCloseUserDialog()"
					class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
			</div>
			<div id="dlg" class="easyui-dialog"
				style="width: 730px;height:280px;padding:10px 10px;" closed="true"
				buttons="#dlg-buttons">
				<form method="post" id="fm">
					<table cellspacing="8px;">
						
						<tr>
							<td>权重：</td>
							<td><input type="text" id="priority" name="priority"
								class="easyui-validatebox" required="true" /> <span
								style="color: red">*</span>
							</td>
							<td>	</td>
							<td>名称：</td>
							<td><select name="areaName" class="easyui-combobox"
								id="areaName" style="width: 154px;" editable="false"
								panelHeight="auto"}>
									<option value="">请选择区域</option>
									<option value="1"> 北京</option>
									<option value="2">上海</option>
									<option value="3">深圳</option>
									<option value="4">广州</option>
							</select>  <span style="color: red">*</span>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div id="dedlg" class="easyui-dialog"
				style="width: 730px;height:280px;padding:10px 10px;" closed="true"
				buttons="#dlg-delbuttons">
				<form method="post" id="delfm">
					<table cellspacing="8px;">
						
						<tr>
							<td>权重：</td>
							<td><input type="text" id="editPriority" name="priority"
								class="easyui-validatebox" required="true" /> <span
								style="color: red">*</span>
							</td>
							<td>	</td>
							<td>名称：</td>
							<td><select name="areaName" class="easyui-combobox"
								id="editAreaName" style="width: 154px;" editable="false"
								panelHeight="auto"}>
									<option value="">请选择区域</option>
									<option value="1"> 北京</option>
									<option value="shanghai">上海</option>
									<option value="3">深圳</option>
									<option value="4">广州</option>
							</select>  <span style="color: red">*</span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>


	</body>
</html>