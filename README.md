# EthTest
Задачей было разработать прототип мобильного приложения по отображению транзакций аккаунта в блокчеин-сети Ethereum.

Используется следующий стек:
* Kotlin как основной язык
* Koin для работы с DI
* Compose для работы с UI
* kotlin-coroutines для многопоточности
* Cicerone для навигации
* Retrofit для работы с сестью
* web3j для декодирования транзакций

На первом экране можно ввести адрес аккаунта в блокчейн-сети Ethereum, при успешной валидации становится активна кнопка "Load transactions". По нажатию происходит переход на следующий экран. В случае ошибки отображается текст ошибки и кнопка "Refresh" для новой попытки запроса данных. В случае успеха отображается кликабельный список транзакций. По клику на транзакцию можно увидеть дополнительную информацию о ней.


<img src="https://github.com/nullsafety/EthTest/assets/41988736/d1813c2e-dfaf-422f-a22b-0b19249a269e" width="300">
<img src="https://github.com/nullsafety/EthTest/assets/41988736/bed7504d-a43b-4f1d-89b1-d436f27f13ae" width="300">
<img src="https://github.com/nullsafety/EthTest/assets/41988736/f35f804b-63bc-4ddb-ad8d-37ddadfcda88" width="300">
