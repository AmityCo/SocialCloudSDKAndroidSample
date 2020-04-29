package com.ekoapp.sample.chatfeature.channellist.filter.excludetags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ekoapp.sample.chatfeature.R
import com.ekoapp.sample.chatfeature.databinding.FragmentExcludeTagsFilterBinding
import com.ekoapp.sample.core.preferences.SimplePreferences
import com.google.common.base.Joiner

class ExcludeTagFilterFragment : Fragment() {

    lateinit var binding: FragmentExcludeTagsFilterBinding
    lateinit var viewModel: ExcludeTagFilterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(ExcludeTagFilterViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exclude_tags_filter, container, false)
        binding.model = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCachedValue()
    }

    private fun setCachedValue() {
        val tags = Joiner.on(",").join(SimplePreferences.getExcludingChannelTags().get())
        viewModel.excludingTags.postValue(tags)
    }

}