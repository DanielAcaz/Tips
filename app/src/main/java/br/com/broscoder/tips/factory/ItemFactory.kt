package br.com.broscoder.tips.factory

import android.content.Context
import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.error.ScenarioException
import br.com.broscoder.tips.service.RestaurantItemsMockService
import br.com.broscoder.tips.service.TipsService

class ItemsFactory(val context: Context): TipsFactory {

    override fun createServiceBy(scenario: Scenario) : TipsService {

        val service = when(scenario){
            Scenario.MOCK -> RestaurantItemsMockService(context)
            else -> throw ScenarioException()
        }

        return service

    }
}