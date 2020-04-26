package com.ignacio.cellularautomata

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MyViewModelTest {
    private lateinit var viewModel: MyViewModel
    private val example90 = "01011010".reversed()
    private val example7 =  "00000111".reversed()
    private val example32 = "00100000".reversed()
    private val example255 = "11111111".reversed()

    @Before
    fun setup() {
        viewModel = MyViewModel()
    }

    @Test
    fun filRulesIsDoingRightFor90() {
        val n = 90
        viewModel.fillRules(n)
        println(viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
        assertEquals(example90, viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
    }

    @Test
    fun filRulesIsDoingRightFor7() {
        val n = 7
        viewModel.fillRules(n)
        println(viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
        assertEquals(example7, viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
    }

    @Test
    fun filRulesIsDoingRightFor32() {
        val n = 32
        viewModel.fillRules(n)
        println(viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
        assertEquals(example32, viewModel.ruleset.map { boolToChar(it) }.joinToString(""))
    }

    private fun boolToChar(b: Boolean): Char {
        return if(b) '1' else '0'
    }

    @Test
    fun evolveCellTest90() {
        viewModel.ruleNumber = 90
        viewModel.simulationSize = 7
        viewModel.initializeSimulation()
        viewModel.writeCells()


        var res = viewModel.evolveCell(6, 0, 1)
        println(res)
        assertEquals(example90[0], boolToChar(res))

        res = viewModel.evolveCell(0, 1, 2)
        println(res)
        assertEquals(example90[0], boolToChar(res))

        res = viewModel.evolveCell(1, 2, 3)
        println(res)
        assertEquals(example90[1], boolToChar(res))

        res = viewModel.evolveCell(2, 3, 4)
        println(res)
        assertEquals(example90[2], boolToChar(res))

        res = viewModel.evolveCell(3, 4, 5)
        println(res)
        assertEquals(example90[4], boolToChar(res))
    }

    @Test
    fun evolveCellTest90CustomEntry() {
        viewModel.ruleNumber = 90
        viewModel.simulationSize = 7
        viewModel.initializeSimulation(booleanArrayOf(true,true,false,false,true,false,true,true))
        viewModel.writeCells()


        var res = viewModel.evolveCell(6, 0, 1)
        println(res)
        assertEquals(example90[7], boolToChar(res))

        res = viewModel.evolveCell(0, 1, 2)
        println(res)
        assertEquals(example90[6], boolToChar(res))

        res = viewModel.evolveCell(1, 2, 3)
        println(res)
        assertEquals(example90[4], boolToChar(res))

        res = viewModel.evolveCell(2, 3, 4)
        println(res)
        assertEquals(example90[1], boolToChar(res))

        res = viewModel.evolveCell(3, 4, 5)
        println(res)
        assertEquals(example90[2], boolToChar(res))
    }

    @Test
    fun evolveCellTest255() {
        viewModel.ruleNumber = 255
        viewModel.simulationSize = 7
        viewModel.initializeSimulation(booleanArrayOf(true,true,false,false,true,false,true,true))
        viewModel.writeCells()


        var res = viewModel.evolveCell(6, 0, 1)
        println(res)
        assertEquals(example255[7], boolToChar(res))

        res = viewModel.evolveCell(0, 1, 2)
        println(res)
        assertEquals(example255[6], boolToChar(res))

        res = viewModel.evolveCell(1, 2, 3)
        println(res)
        assertEquals(example255[4], boolToChar(res))

        res = viewModel.evolveCell(2, 3, 4)
        println(res)
        assertEquals(example255[1], boolToChar(res))

        res = viewModel.evolveCell(3, 4, 5)
        println(res)
        assertEquals(example255[2], boolToChar(res))
    }


}