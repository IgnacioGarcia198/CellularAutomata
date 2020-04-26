package com.ignacio.cellularautomata

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import timber.log.Timber

class MyViewModel: ViewModel() {
    var simulationSize = MIN_SIZE
    var simulationGenerations = MIN_GENERATIONS
    companion object {
        const val MIN_SIZE = 3
        const val MAX_SIZE = 47
        const val MIN_GENERATIONS = 1
        const val MAX_GENERATIONS = 499
    }
    @VisibleForTesting
    val ruleset = BooleanArray(8)
    private lateinit var cells: BooleanArray

    private var generation = 0
    private lateinit var view: View
    var ruleNumber = 0
    var cellSide = 0

    val viewModelJob = SupervisorJob()
    val viewmodelScope = CoroutineScope(Dispatchers.Default+ viewModelJob)

    fun cancelTheJob() {
        // we only cancel our child coroutines but not the whole job! Because we are sharing this ViewModel.
        // although sharing ViewModel is the preferred way of Fragment communication, it's not always the best.
        // when we would like to minimize dependencies between Fragment variables while keeping state, for example.
        // also, there is no big need to share ViewModel when data to pass is scarce and Fragment represent different screens.
        viewmodelScope.launch {
            viewModelJob.cancelChildren()
        }
    }

    fun initializeSimulation(boolArary: BooleanArray = booleanArrayOf()) {
        resetSimulation()
        fillRules(ruleNumber)
        initializeCells(boolArary)
    }

    fun initializeView(view: View, width: Int) {
        this.view = view
        calculateCellSide(width)
    }

    fun startSimulation() {
        viewmodelScope.launch {
            evolve()
        }
    }

    private fun initializeCells(boolArary: BooleanArray = booleanArrayOf()) {
        if(boolArary.isNotEmpty()) cells = boolArary
        else {
            cells = BooleanArray(simulationSize)
            cells[simulationSize/2] = true
        }
    }

    fun fillRules(number: Int) {
        var n = number
        var i = 0
        while (n > 0) {
            ruleset[i++] = n%2 != 0
            n /= 2
        }
        while(i < 8) {
            ruleset[i++] = false
        }
    }

    private fun boolToBinary(b: Boolean): Short {
        return if(b) 1 else 0
    }

    private fun boolToInt(left: Boolean, current: Boolean, right: Boolean) : Int {
        return boolToBinary(left) * 4 + boolToBinary(current) * 2 + boolToBinary(right)
    }

    @VisibleForTesting
    fun evolveCell(left: Int, current: Int, right: Int): Boolean {
        val leftValue = cells[left]
        val currentValue = cells[current]
        val rightValue = cells[right]
        val index = boolToInt(leftValue, currentValue, rightValue)
        return ruleset[index]
    }

    private fun nextGeneration() {
        val nextGen = BooleanArray(simulationSize)
        nextGen[0] = evolveCell(simulationSize-1, 0, 1)
        nextGen[simulationSize-1] = evolveCell(simulationSize-2, simulationSize-1, 0)
        for(i in 1..simulationSize-2) {
            nextGen[i] = evolveCell(i-1, i, i+1)
        }
        cells = nextGen
        generation ++
    }

    private suspend fun paintCells() {
        withContext(Dispatchers.Main) {
            view.paintGeneration(generation, cells)
        }
    }

    private suspend fun evolve() {
        paintCells()
        while(generation < simulationGenerations) {
            delay(20)
            nextGeneration()
            paintCells()
        }
    }

    override fun onCleared() {
        super.onCleared()
        // only cancel the whole job when app exits
        viewModelJob.cancel()
    }

    private fun calculateCellSide(width: Int) {
        cellSide = if(simulationSize > 0) width/simulationSize else 0
    }

    private fun resetSimulation() {
        generation = 0
    }

    interface View {
        fun paintGeneration(generation: Int, cells: BooleanArray)
    }

    @TestOnly
    fun writeCells() {
        println(cells.map { boolToBinary(it) }.joinToString(""))
    }

}