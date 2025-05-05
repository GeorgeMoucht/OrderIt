from django.shortcuts import render, redirect
import json
from django.http import JsonResponse
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.contrib.auth import get_user_model
from django.contrib.auth import logout
from .decorators import superuser_required
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import get_object_or_404
from .serializers import UserUpdateSerializer
from rest_framework.parsers import JSONParser
from django.views.decorators.http import require_POST
from django.core.paginator import Paginator
from django.utils.http import urlencode


User = get_user_model()

def custom_permission_denied_view(request, exception=None):
    return render(request, 'web/403.html', status=403)

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
def manage_users(request):
    user_list = User.objects.order_by("id")
    paginator = Paginator(user_list, 10)

    page_number = request.GET.get("page")
    page_obj = paginator.get_page(page_number)

    return render(request, "web/manage_users.html", {
        # "page_obj": page_obj,
        # "users": page_obj.object_list,
        # "active_page": "users"
        "users": page_obj,
        "active_page": "users"
    })

@login_required
@superuser_required
def dashboard(request):
    return render(request, "web/dashboard.html", {
        "active_page": "dashboard"
    })


@csrf_exempt
@require_POST
@login_required
@superuser_required
def update_user_view(request):
    try:
        data = json.loads(request.body)
        user_id = data.get("id")
        user = get_object_or_404(User, id=user_id)

        if user.is_superuser:
            return JsonResponse({
                "success": False,
                "message": "You cannot modify a superuser."
            }, status=403)

        serializer = UserUpdateSerializer(user, data=data)

        if serializer.is_valid():
            serializer.save()
            print("💾 Saved user with new role:", user.role)
            return JsonResponse({
                'success': True,
                'message': 'User updated successfully'
            })
        else:
            return JsonResponse({
                'success': False,
                'message': serializer.errors
            }, status=400)

    except json.JSONDecodeError:
        return JsonResponse({
            'success': False,
            'message': 'Invalid JSON payload.'
        }, status=400)

    except Exception as e:
        return JsonResponse({
            'success': False,
            'message': str(e)
        }, status=400)
    

@csrf_exempt
@require_POST
@login_required
@superuser_required
def delete_user_view(request):
    try:
        data = json.loads(request.body)
        print("Incoming data:", data)

        user_id = data.get("id")

        if not user_id:
            return JsonResponse({
                "success": False,
                "message": "Missing user ID."
            }, status=400)
        
        user = get_object_or_404(User, id=user_id)

        if user.is_superuser:
            return JsonResponse({
                "success": False,
                "message": "Cannot delete a superuser."
            }, status=403)

        user.delete()

        return JsonResponse({
            "success": True,
            "message": "User deleted successfully."
        })

    except Exception as e:
        return JsonResponse({
            "success": False,
            "message": str(e)
        }, status=400)

@csrf_exempt
@require_POST
@login_required
@superuser_required
def create_user_view(request):
    try:
        data = json.loads(request.body)
        user = User.objects.create_user(
            username=data["username"],
            first_name=data.get("first_name", ""),
            last_name=data.get("last_name", ""),
            email=data.get("email", ""),
            password=data["password"],
            role=data.get("role", "waiter")
        )
        return JsonResponse({"success": True})
    except Exception as e:
        return JsonResponse({"success": False, "message": str(e)}, status=400)


@login_required
@superuser_required
def logout_view(request):
    logout(request)
    return redirect('login')
