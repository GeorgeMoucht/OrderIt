from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0001_initial'),  # άλλαξέ το με το όνομα του app σου και το σωστό dependency
    ]

    operations = [
        migrations.CreateModel(
            name='MenuCategory',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=50, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='MenuItem',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=100)),
                ('description', models.TextField(blank=True)),
                ('price', models.DecimalField(max_digits=6, decimal_places=2)),
                ('image', models.ImageField(blank=True, null=True, upload_to='menu/')),
                ('category', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='api.MenuCategory')),
            ],
        ),
    ]
