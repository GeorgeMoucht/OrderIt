from django.contrib.auth.decorators import user_passes_test
from django.core.exceptions import PermissionDenied

def superuser_required(view_func):
    """
    Decorator that allow access only to superusers.
    Raises 403 Permission Denied for others.
    """
    def _wrapped_view(request, *args, **kwargs):
        if not request.user.is_superuser:
            raise PermissionDenied # Triggers the 403 page
        return view_func(request, *args, **kwargs)
    return _wrapped_view