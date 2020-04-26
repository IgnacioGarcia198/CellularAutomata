package com.ignacio.cellularautomata

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), MyViewModel.View {

    private val viewModel: MyViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var viewLayoutParams: LinearLayout.LayoutParams
    private val horizontalLayoutParams =
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.cancelTheJob()
            findNavController().popBackStack()
        }
        return inflater.inflate(R.layout.fragment_second, container, false)
        //simulationView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    viewModel.initializeView(this@SecondFragment, requireView().width)
                    viewLayoutParams = LinearLayout.LayoutParams(viewModel.cellSide, viewModel.cellSide)
                    viewModel.startSimulation()
                }
            }
        )
    }

    override fun paintGeneration(generation: Int, cells: BooleanArray) {
        val horizontalLayout = LinearLayout(requireContext())
        horizontalLayout.orientation = LinearLayout.HORIZONTAL
        horizontalLayout.layoutParams = horizontalLayoutParams

        for(i in 0 until viewModel.simulationSize) {
            val view = View(requireContext())
            view.layoutParams = viewLayoutParams
            view.setBackgroundColor(if(cells[i]) Color.BLACK else Color.WHITE)
            horizontalLayout.addView(view)
        }
        thecontent.addView(horizontalLayout)
        thecontentscroll.fullScroll(View.FOCUS_DOWN)
    }

    override fun onStop() {
        viewModel.cancelTheJob()
        super.onStop()
    }
}
