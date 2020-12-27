<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="/common/template :: header(~{::title},~{::link},~{::style})">
</head>
<body>
    <div class="alan-detail-page">
        <div class="alan-detail-title">基本信息</div>
        <table class="layui-table alan-detail-table">
            <colgroup>
                <col width="100px"><col>
                <col width="100px"><col>
            </colgroup>
            <tbody jsoup="detail"></tbody>
        </table>
    </div>
<script th:replace="/common/template :: script"></script>
</body>
</html>