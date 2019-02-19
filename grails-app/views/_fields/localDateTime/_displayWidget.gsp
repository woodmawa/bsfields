<%@ page import="java.time.LocalDate; java.time.format.DateTimeFormatter" %>
<g:set var="localDatePattern" value="${message(code: 'default.localDateTime.format', default: 'yyyy-MM-dd HH:mm')}"/>
<g:if test="${actionName == 'index'}">
    ${value?.format(DateTimeFormatter.ofPattern(localDatePattern, request.getLocale()))}
</g:if>
<g:elseif test="${actionName == 'list'}">
    ${value?.format(DateTimeFormatter.ofPattern(localDatePattern, request.getLocale()))}
</g:elseif>
<g:else>
    <div class="container fieldcontain col-sm-12" >
        <div class="form-group form-inline">
            <div class="input-group text col-sm-8"  >
                <input name="${property}" type='datetime' readonly class="form-control" value="${value }"/>
                <div class="input-group-append" >
                    <button class="btn btn-icon-fixed-width btn-outline-secondary btn-block" type="button" >
                        <i class="fas fa-calendar"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</g:else>