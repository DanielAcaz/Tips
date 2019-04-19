package br.com.broscoder.tips.factory

import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.service.TipsService

interface  TipsFactory {

    fun createServiceBy(scenario: Scenario) : TipsService

}