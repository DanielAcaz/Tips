package br.com.broscoder.tips.factory

import android.content.Context
import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.error.ScenarioException
import br.com.broscoder.tips.service.MenuMockService
import br.com.broscoder.tips.service.MenuService

class MenuFactory(val context: Context): TipsFactory {

    override fun createServiceBy(scenario: Scenario): MenuService {

        val service = when(scenario){
            Scenario.MOCK -> MenuMockService(context)
            else -> throw ScenarioException()
        }

        return service

    }
}