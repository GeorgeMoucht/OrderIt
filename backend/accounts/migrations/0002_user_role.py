# Generated by Django 5.1.7 on 2025-05-02 22:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='role',
            field=models.CharField(choices=[('superadmin', 'Super Admin'), ('waiter', 'Waiter'), ('bar', 'Bar'), ('kitchen', 'Kitchen')], default='waiter', max_length=20),
        ),
    ]
