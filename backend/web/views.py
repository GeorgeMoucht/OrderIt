from django.shortcuts import render, redirect
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.contrib.auth import get_user_model
from django.contrib.auth import logout
from .decorators import superuser_required

User = get_user_model()

def login_view(request):
    if request.method == 'POST':
        username = request.POST.get("username")
        password = request.POST.get("password")
        user = authenticate(request, username=username, password=password)

        if user is not None:
            if user.is_superuser:
                login(request, user)
                return redirect('dashboard')
            else:
                return render(request, 'web/login.html', {
                    'error': 'You do not have permission to access the admin dashboard.'
                })
        else:
            return render(request, 'web/login.html', {
                'error': 'Invalid credentials'
            })
    return render(request, 'web/login.html')


@login_required
@superuser_required
def dashboard(request):
    return render(request, "web/dashboard.html", {
        "active_page": "dashboard"
    })

@login_required
@superuser_required
def manage_users(request):
    users = User.objects.all()
    return render(request, "web/manage_users.html", {
        "users": users,
        "active_page": "users"
    })


@login_required
@superuser_required
def logout_view(request):
    logout(request)
    return redirect('login')

def custom_permission_denied_view(request, exception=None):
    return render(request, 'web/403.html', status=403)