Mock classes for in-JVM gwt testing

##Motivation
Testing GWT code is cumbersome. You have to following options basically:
* Follow a clean MVP architecture. This is your best option. But you sometimes fail to do so, or you inherit a codebase that's not clean. And even if the entire codebase is clean MVP and is covered with unit tests, you might need integration tests to assert that the user actually gets the proper outcome from a cooperation of various controllers.
* GWTTestCase - This actually compiles your code and runs it with HtmlUnit. You can assert various useful things. However your test development is slow, the test executions are slow and you cannot debug your tests.
* Selenium - Developing Selenium tests is even more cumbersome than GWTTestCases, though you can assert even more useful stuff.

After all, if the code is not clean MVP, you cannot assert things like 'given this and that a data, that save button there will be disabled'. You can of course start refactoring to clean MVP, but it's not always safe to do so, as the code is not covered with tests.

With gwt-mock, I wanted to find a way to develop and run tests fast for existing GWT code.

##The solution in general

[...]
