package com.example.poodle.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.poodle.R
import com.example.poodle.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fullImage.load(getString(R.string.image_url, args.breed.referenceImageId)) {
            error(
                R.drawable.paw
            )
        }
        binding.breedName.text = args.breed.name
        binding.otherInfo.text = getString(
            R.string.other_info,
            args.breed.bredFor,
            args.breed.temperament,
            args.breed.lifeSpan
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}