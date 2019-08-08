<%@ page language="java" contentType="text/html; charset=utf-8"
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js" charset='utf-8'></script>
    	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easyui.min.js" charset='utf-8'></script>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/easyui.css" charset='utf-8'>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/icon.css" charset='utf-8'>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/demo.css" charset='utf-8'>
		<title>propertyGrid</title>
		<script type="text/javascript">
			var index = 0;
			function addPanel() {
				index++;
				$("#tt").tabs('add', {
					title: 'Tab' + index,
					content: '<div style="padding:10px">Content' + index + '</div>',
					closable: true
				});
				
			}
			
			function removePanel() {
				var tab = $("#tt").tabs('getSelected');
				console.log(tab);
				if (tab) {
					var index = $("tt").tabs('getTabIndex', tab);
					$("#tt").tabs('close', index);
				}
			}
		
		</script>
		
	</head>
	<body>
		<h2>basic Tabs</h2>
		<div style="margin:20px 0 10px 0;"></div>
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:700px;height:250px">
			<div title="easyui-tabs" style="padding:10px">
				<p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
				<ul>
					<li>easyui is a collection of user-interface plugin based on jQuery.</li>
					<li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
					<li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
					<li>complete framework for HTML5 web page.</li>
					<li>easyui save your time and scales while developing your products.</li>
					<li>easyui is very easy but powerful.</li>
				</ul>
			</div>
			
			<div title="My documents" style="padding:10px">
			
			
				Second tab
			
			</div>
			
			<div title="Help" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
				This is the help content
			</div>

		</div>
			<div id="tab-tools">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePanel()"></a>
			</div>
	
	</body>
</html>