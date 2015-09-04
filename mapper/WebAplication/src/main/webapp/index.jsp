<%@page import="java.util.Date"%>
<%@page import="de.heinfricke.countriesmapper.reader.*"%>
<%@page import="de.heinfricke.countriesmapper.preparer.*"%>
<%@page import="de.heinfricke.countriesmapper.country.*"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<style type="text/css">
body {
	background-image:
		url('http://cdn3.crunchify.com/wp-content/uploads/2013/03/Crunchify.bg_.300.png');
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Countries Directory at FTP Maker</title>
</head>
<body>
	<br>
	<center>
		<h3>Countries Directories at FTP Maker</h3>

		<br /> <br />


		<form name="form1" id="form1" action="upload" method="post"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td width="150px">Chose your file:</td>
					<td><input type="file" size="50" name="file1"></td>
				</tr>
				<tr>
					<td><br /></td>
				</tr>
				<tr>
				<tr>
					<td>Host:</td>
					<td><input type="text" name="host"></td>
				</tr>
				<tr>
					<td>Port:</td>
					<td><input type="text" name="port"></td>
				</tr>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<td>Path:</td>
					<td><input type="text" name="path"></td>
				</tr>
				<tr>
					<td>What to do with old directories?</td>
					<td><select name="directories">
							<option value="delete">Delete</option>
							<option value="replace">Replace</option>
							<option value="add">Add new contents</option>
					</select></td>
				</tr>
				<tr>
					<td>Create CSV file:</td>
					<td><input type="checkbox" name="csv"></td>
				</tr>
				<tr>
					<td>Create XML file:</td>
					<td><input type="checkbox" name="xml"></td>
				</tr>
				<tr>
					<td><br /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload"></td>
				</tr>
			</table>
		</form>

	</center>
</body>
</html>