<%@ page import="java.time.LocalDateTime; java.time.format.DateTimeFormatter" %>
<g:set var="localDateTimePattern" value="${message(code: 'default.localDateTime.format',default: 'yyyy-MM-dd HH:mm')}"/>
<g:set var="propertyName" value="${pageScope.propertyName} " />
<g:set var="label" value="${pageScope.label} " />

<%-- <jdt:jdtScaffoldField propertyName="$propertyName" label="${label}" value="${propValue}"></jdt:jdtScaffoldField>  --%>

<g:set var="localDateTimePattern" value="${message(code: 'default.localDateTime.format',default: 'yyyy-MM-dd HH:mm')}"/>
<div class="container fieldcontain col-sm-12" >
    <div class="form-group form-inline">
        ${widget}  <%-- use delegated widget rendering stored on model by <f:field--%>

    </div>
</div>
