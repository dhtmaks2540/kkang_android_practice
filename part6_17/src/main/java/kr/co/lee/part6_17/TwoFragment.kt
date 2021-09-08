package kr.co.lee.part6_17

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kr.co.lee.part6_17.databinding.DialogFragmentBinding

// DialogFragment 상속 후 onCreateDialog에서 정보 입력 -> dialog를 반환
class TwoFragment : DialogFragment() {

    private var _binding: DialogFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.dialogBtn1.setOnClickListener {
            Toast.makeText(activity?.baseContext!!, "확인버튼", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}