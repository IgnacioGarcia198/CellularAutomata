package com.ignacio.cellularautomata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private val viewModel: MyViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sizeSeekbar.max = MyViewModel.MAX_SIZE
        generationsSeekbar.max = MyViewModel.MAX_GENERATIONS
        ruleSeekbar.max = 255


        sizeSeekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.simulationSize = progress + MyViewModel.MIN_SIZE
                    seekbarChangeTextView.text = getString(R.string.size_change, viewModel.simulationSize)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.VISIBLE
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.GONE
                }
            }
        )
        sizeSeekbar.progress

        generationsSeekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.simulationGenerations = progress + MyViewModel.MIN_GENERATIONS
                    seekbarChangeTextView.text = getString(R.string.generations_change, viewModel.simulationGenerations)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.VISIBLE
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.GONE
                }
            }
        )

        ruleSeekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    viewModel.ruleNumber = progress
                    seekbarChangeTextView.text = getString(R.string.rule_change, viewModel.ruleNumber)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.VISIBLE
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekbarChangeTextView.visibility = View.GONE
                }
            }
        )

        sizeSeekbar.progress = (MyViewModel.MAX_SIZE + MyViewModel.MIN_SIZE)/2
        generationsSeekbar.progress = (MyViewModel.MAX_GENERATIONS + MyViewModel.MIN_GENERATIONS)/2
        ruleSeekbar.progress = 127

        startSimulationButton.setOnClickListener {
            viewModel.initializeSimulation()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}
