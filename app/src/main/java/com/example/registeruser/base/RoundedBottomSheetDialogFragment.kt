package com.example.registeruser.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.registeruser.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class RoundedBottomSheetDialogFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>,
    val screenSize: SheetSizes? = SheetSizes.FULLSCREEN
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

//    protected lateinit var sharedManager: SharedManager
//    protected lateinit var repository: ApiRepository
//    lateinit var viewModel: BaseViewModel
    private lateinit var notAttachedContext: Context

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notAttachedContext = requireContext()

        dialog?.setOnShowListener {
            notAttachedContext.applicationContext
            if (screenSize == SheetSizes.FULLSCREEN) {
                setupFullHeight(notAttachedContext, it as BottomSheetDialog)
            } else if (screenSize == SheetSizes.HALF) {
                setupHalfHeight(notAttachedContext, it as BottomSheetDialog)
            } else {
                setupDefaultHeight(it as BottomSheetDialog)
            }

            initialize()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        if ((activity as MainActivity).viewModel != null)
//            viewModel = (activity as MainActivity).viewModel!!
//        sharedManager = (activity as MainActivity).sharedManager
//        repository = (activity as MainActivity).repository
        return BottomSheetDialog(requireContext(), theme)
    }

    private fun setupFullHeight(context: Context, bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight(context)

        if(windowHeight == 0)
            setupDefaultHeight(bottomSheetDialog)
        else {
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setupDefaultHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = bottomSheet.height
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setupHalfHeight(context: Context, bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight(context) / 2

        if(windowHeight == 0){
            setupDefaultHeight(bottomSheetDialog)
        }else {
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
    private fun getWindowHeight(context: Context): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
            (context as Activity?)!!.windowManager.defaultDisplay
                .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    abstract fun initialize()

    enum class SheetSizes {
        FULLSCREEN, HALF, DEFAULT
    }
}