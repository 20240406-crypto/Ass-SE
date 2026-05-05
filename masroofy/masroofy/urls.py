from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),

    # ربط app البادجيت
    path('', include('budget.urls')),
]