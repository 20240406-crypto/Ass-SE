from django.urls import path
from . import views

urlpatterns = [
    path('set-budget/', views.setBudget, name='set-budget'),
    path('dashboard/', views.dashboard, name='dashboard'),
    path('UserLogin/', views.CreateUser, name='User-Login'),
]