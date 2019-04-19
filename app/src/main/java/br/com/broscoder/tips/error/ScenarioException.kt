package br.com.broscoder.tips.error

class ScenarioException : RuntimeException() {

    override val message: String?
        get() = "INVALID SCENARIO"
}