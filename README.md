# OMSService
Order Management System service.

## Описание
Данный сервис принимает информацию о заказах со статусом "payed", "assigned" или "canceled", записывает её в БД и 
при дальнейшем изменении статуса заказа отправляет его в очередь.
Этот сервис работает в паре с [клиентом](https://github.com/KorobkovD/OMSClient), в котором пользователь (менежджер) как раз
изменяет статус заказа (payed на assigned или canceled).

## Список ресурсов
**POST** _/changedorder_ - принимает JSON заказа. Забирает из него только поля, необходимые для работы конкретно 
этой части системы: идентификатор заказа, статус и идентификатор сотрудника, которому назначен заказ.

## Принимаемая информация
Пример JSON, в котором содержится информация о заказе, которому назначен работник:
        {
            "order_id":"25",
            "status":"payed"
        }
В этом случае в БД появляется запись о заказе. Сотрудник, воспользовавшись [клиентом](https://github.com/KorobkovD/OMSClient),
может назначить исполнителя заказа. Тогда на сервис будет послан JSON вида:
        {
            "order_id":"25",
            "status":"payed",
            "worker_id":"2"
        }
