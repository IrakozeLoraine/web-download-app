<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 6/20/2022
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>WDM</title>
    <%@ include file="../components/Header.jsp" %>
</head>
<body>
<header>
    <!-- Navbar -->
    <jsp:include page="../components/Navbar.jsp"/>
    <!-- Navbar -->


    <div class="bg-white dark:bg-gray-800 ">
        <div class="text-center w-full mx-auto py-12 px-4 sm:px-6 lg:py-32 lg:px-8 z-20">
            <h2 class="text-3xl font-extrabold text-black dark:text-white sm:text-4xl">
            <span class="block">
                Enter the URL website you want to download
            </span>
                <span class="block text-indigo-500 mt-4">
                It&#x27;s today or never.
            </span>
            </h2>
            <p class="text-lg mt-4 max-w-md mx-auto text-gray-400">
                Copy the link of a website you want to download and you will quicky have it on your device is seconds.
            </p>
            <div class="my-20 w-[80%] mx-auto">
                <form class="mt-12 rounded-md shadow" action="/download-website" method="post">
                    <label for="default-search" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-gray-300">Download</label>
                    <div class="relative">
                        <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M13 13v5.585l1.828-1.828 1.415 1.415L12 22.414l-4.243-4.242 1.415-1.415L11 18.585V13h2zM12 2a7.001 7.001 0 0 1 6.954 6.194 5.5 5.5 0 0 1-.953 10.784v-2.014a3.5 3.5 0 1 0-1.112-6.91 5 5 0 1 0-9.777 0 3.5 3.5 0 0 0-1.292 6.88l.18.03v2.014a5.5 5.5 0 0 1-.954-10.784A7 7 0 0 1 12 2z" fill="rgba(255,255,255,1)"/></svg>
                        </div>
                        <input type="search" id="default-search" name="websiteUrl" class="block p-4 pl-12 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter URL link...." required="">
                        <button type="submit" class="text-white absolute right-2.5 bottom-2.5 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Download</button>
                    </div>
                </form>
                <c:if test="${not empty error}">
                    <p class="text-red-700">
                        Error: ${error}
                    </p>
                </c:if>
            </div>
        </div>
    </div>
</header>
</body>
</html>