package di

import feature.agent.presentation.AgentsListViewModel
import feature.feedback.presentation.FeedbackViewModel
import feature.home.presentation.HomeViewModel
import feature.hoyolab.presentation.HoYoLabSyncViewModel
import feature.hoyolab.presentation.MyAgentDetailViewModel
import feature.hoyolab.presentation.MyAgentsListViewModel
import feature.setting.presentation.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import root.InitViewModel
import root.MainContainerViewModel
import feature.dailytracker.presentation.DailyTrackerViewModel

val viewModelModule = module {
    viewModelOf(::InitViewModel)
    viewModelOf(::MainContainerViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AgentsListViewModel)
    viewModelOf(::SettingViewModel)
    viewModelOf(::FeedbackViewModel)
    viewModelOf(::HoYoLabSyncViewModel)
    viewModelOf(::MyAgentsListViewModel)
    viewModelOf(::MyAgentDetailViewModel)
    viewModelOf(::DailyTrackerViewModel)
}
