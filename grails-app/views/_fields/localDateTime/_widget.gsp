<%@ page import="java.time.format.DateTimeFormatter" %>
<%-- edit input 'widget' for localDateTime --%>

<g:set var="localDateTimePattern" value="${message(code: 'default.localDateTime.format',default: 'yyyy-MM-dd HH:mm')}"/>

<div class="input-group date col-sm-8"  >
    <input id="${property}-label" name="${property}" type='datetime-local' class="form-control" value="${value}" placeholder="<empty>"/>
    <div class="input-group-append" >
        <button class="btn btn-icon-fixed-width btn-outline-secondary btn-block" disabled aria-disabled="true" type="button" >
            <i class="fas fa-calendar"></i>
        </button>
    </div>
</div>
