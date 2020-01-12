<#import "parts/common.ftl" as c>

<@c.page>
    <h3 class="mb-4">Страница сообщений</h3>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Поиск по тегу">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary mb-3" data-toggle="collapse"
       href="#collapseExample" role="button"
       aria-expanded="false" aria-controls="collapseExample"
    >
        Добавить новое сообщение
    </a>

    <div class="collapse" id="collapseExample">
        <div class="form-droup">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" name="text" class="form-control" placeholder="Введите сообщение"/>
                </div>
                <div class="form-group">
                    <input type="text" name="tag" class="form-control" placeholder="Тэг"/>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile" />
                        <label class="custom-file-label" for="customFile">Выбрать файл</label>
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}" />

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top"/>
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>

</@c.page>