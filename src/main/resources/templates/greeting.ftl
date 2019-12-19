<#import "parts/common.ftl" as c>

    <@c.page>
        <h2>Привет, ${username}</h2>
        <p><strong>This is a simple clone off Instagram<br /></strong></p>
        <p><strong></strong></p>
        <p><strong>О продукте:<br /></strong>
            <br />Спасибо, что выбрали наш продукт! Social - социальная сеть, которая пока что создается по образу и подобию социальной сети Instagram, но когда-нибудь мы придумаем что-нибудь свое :)
            <br />
            <br />Social на данном этапе обладает следующими возможностями: регистрация, авторизация, добавление публикаций, оценка публикаций, а также систему прав и подписок.
            <br />
            <br /><strong>Авторы:</strong>
            <br />
            <br /><a href="https://vk.com/beautifulcraftrus" target="_blank" rel="noopener">Oleg Rakitin</a> - FullStack dev
            <br /><a href="https://vk.com/id77055360" target="_blank" rel="noopener">Vlad Eremenko</a> - FullStack dev
            <br /><a href="https://vk.com/eamoff" target="_blank" rel="noopener">Amiraslanov Elmar</a> - FullStack dev</p>

        <div class="row">
            <div class="col-md-4">

                <h4>Форма обратной связи</h4>
                <form action="/feedback" method="get">
                    <div class="form-group">
                        <label for="name">Ваше имя:</label>
                        <input type="name" name="name" class="form-control" id="name" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="email1">E-mail:</label>
                        <input type="email" name="email" class="form-control" id="email1" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="phone">Номер телефона:</label>
                        <input type="phone" name="phone" class="form-control" id="phone" placeholder="Phone">
                    </div>
                    <div class="form-group">
                        <label for="message">Сообщение:</label>
                        <textarea class="form-control" name="message" rows="3"></textarea>
                    </div>
                    <button type="submit" ${(errorFeedback??)?string( 'is-invalid', '')} class="btn btn-info">Отправить сообщение</button>
                    <#if errorFeedback??>
                        ${errorFeedback}
                    </#if>
                </form>
            </div>
            <div class="col-md-4"> </div>
        </div>

        <p></p>
        <p>
            <figure class="fig">
                <img src="https://cdn-images-1.medium.com/max/1600/0*kmxtSPw6uiyVsUho" alt="" width="623" height="615" />
            </figure>
        </p>

        <style>
            .fig {
                margin-left: 383px;
                display: block;
                /* Блочный элемент (для старых браузеров) */
                text-align: center;
                /* Выравнивание по центру */
                font-style: italic;
                /* Курсивное начертание */
                margin-top: -627px;
                /* Отступ сверху */
                margin-bottom: 0px;
                /* Отступ снизу */
                color: #666;
                /* Цвет подрисуночной подписи */
            }
        </style>

    </@c.page>