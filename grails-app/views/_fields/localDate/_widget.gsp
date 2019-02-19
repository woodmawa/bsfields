<%@ page import="java.time.format.DateTimeFormatter" %>
<%-- edit input 'widget' for localDateTime --%>

<g:set var="localDatePattern" value="${message(code: 'default.localDate.format',default: 'yyyy-MM-dd')}"/>

<div class="input-group date col-sm-8"  >
    <input name="${property}" type='date' class="form-control" value="${value?.format(DateTimeFormatter.ofPattern ("${localDatePattern}")) }" placeholder="<empty>"/>
    <div class="input-group-append" >
        <button class="btn btn-icon-fixed-width btn-outline-secondary btn-block" disabled aria-disabled="true" type="button" >
            <i class="fas fa-calendar"></i>
        </button>
    </div>
</div>
