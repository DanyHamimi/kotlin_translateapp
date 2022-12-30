package fr.danyhamimi.projet_hamimi_kaabeche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.FragmentSecondBinding
import fr.danyhamimi.projet_hamimi_kaabeche.databinding.GameFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameFragment : Fragment() {

    private var _binding: GameFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}