<%@ include file = "common/header.jspf" %>
<%@ include file = "common/navigation.jspf" %>

<div class="container">
    <h1>Enter Todo Details </h1>

<!--     #### 주석부분) 원래는 이렇게함 근데 requestParam말고 todo로 주고싶으니 변경할거임.
    <form method="post">
        Description : <input type="text" name = "description" />
        <input type = "submit" class ="btn btn-success"/>
    </form>   ####### -->

<!--     #### 주석부분 변경된 내용 -->

    <!-- Controller의   public String addNewTodo(ModelMap model,Todo todo){
    의 param todo와 modelAttribute="todo"와 이름일 일치시켜야함
    또 path는 Todo의 변수 description과 일치 또 type="text"추가
    10글자미만일때 에러를 발생하고싶으면? : form:errors 추가-->

    <!-- Controller에서 todo.jsp를 호출할때에는 Controller에 modelMap이 있어야함! -->
    <form:form method="post" modelAttribute="todo">

        <fieldset class="mb-3">
            <form:label path = "description">Description</form:label>
            Description: <form:input type = "text" path="description" required="required"/>
            <form:errors  path="description" cssClass="text-warning"/>
        </fieldset>

        <fieldset class="mb-3">
           <form:label path = "targetDate">Target Date</form:label>
           Description: <form:input type = "text" path="targetDate" required="required"/>
           <form:errors  path="targetDate" cssClass="text-warning"/>
        </fieldset>

        <form:input type = "hidden" path="id"/>
        <form:input type = "hidden" path="done"/>
        <input type="submit" class="btn btn-success"/>
    </form:form>
</div>
<%@ include file = "common/footer.jspf" %>
<script type="text/javascript">
    $('#targetDate').datepicker({
        format: 'yyyy-mm-dd'
    });
</script>
