package com.example.pisti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.pisti.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    Drawable img;//Oyuncunun oynadığı kartalrın img
    Drawable imgCPU;//Cpu nun oynadığı kartların img
    Drawable fadeImg;//Ortadan kaybolan kartların img
    int imgDefault;
    List<Card> player1;//1. oyuncunun elindeki 4 kart
    List<Card> player2;// CPU nun elindeki 4 kart
    List<Card> playedCardDeck;//Ortadaki kartlar
    List<Card> player1Accumulated; //1. oyuncunun kazandığı kartlar
    List<Card> player2Accumulated; //CPU nun kazandığı kartlar
    List<Card> getAllCard;//Ana deste
    List<Card> memory;//CPU nun kağıt sayması için gereken liste
    List<ImageView> imageList;//1. oyuncumuzun imageview listesi
    List<ImageView> imageList2;// 2. oyuncumuzun imageview listesi
    List<ImageView> imageDefault;//Default deck imageview listesi
    int player1Score;//Player1 in skor değerini burada tutuyoruz
    int player2Score;//CPU nun skor değerini burada tutuyoruz

    Card bosKart;//Kartlar atıldıktan sonra index hatası almamak için bos kart set ettim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        imageList = Arrays.asList(binding.pC0, binding.pC1, binding.pC2, binding.pC3);
        imageList2 = Arrays.asList(binding.cpuC0, binding.cpuC1, binding.cpuC2, binding.cpuC3);
        imageDefault = Arrays.asList(binding.default0, binding.default1, binding.default2, binding.default3);

        getAllCard = Card.shuffle();//Kartları karıştırdım

        //Ortadaki desteyi oluşturdum
        playedCardDeck = Card.dealer(getAllCard);//Oyun başı kural olduğu için ortaya 4 kart ekledim
        player1 = Card.dealer(getAllCard);//1. oyuncuya 4 kart dağıttım
        player2 = Card.dealer(getAllCard);//2. oyuncuya 4 kart dağıttım
        bosKart = new Card();//oynadığımız kartların yerini tutuyor
        player1Accumulated = new ArrayList<>();//1. oyuncunun kazandığı kartları topladığımız deste
        player2Accumulated = new ArrayList<>();//2. oyuncunun kazandığı kartların toplandığı deste
        memory = new ArrayList<>();//CPU nun kağıt sayması için gereken liste


        for (int i = 0; i < 4; i++) {
            assignImagesMiddle(binding.playedCardDeckImage, i);
        }

        for (int i = 0; i < 4; i++) {
            assignImages(imageList.get(i), i);
        }

        for (int i = 0; i < 4; i++) {
            assignImagesCPU(imageList2.get(i), i);
            binding.cpuC0.setVisibility(View.INVISIBLE);
            binding.cpuC1.setVisibility(View.INVISIBLE);
            binding.cpuC2.setVisibility(View.INVISIBLE);
            binding.cpuC3.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < 4; i++) {
            imgDefault = R.drawable.back_side;

            binding.default0.setImageResource(imgDefault);
            binding.default1.setImageResource(imgDefault);
            binding.default2.setImageResource(imgDefault);
            binding.default3.setImageResource(imgDefault);
        }//Default destenin görünümünü oluşturuyorum oyun başında


        ImageView p_c0 = findViewById(R.id.p_c0);
        ImageView p_c1 = findViewById(R.id.p_c1);
        ImageView p_c2 = findViewById(R.id.p_c2);
        ImageView p_c3 = findViewById(R.id.p_c3);
        p_c0.setOnClickListener(this);
        p_c1.setOnClickListener(this);
        p_c2.setOnClickListener(this);
        p_c3.setOnClickListener(this);

        binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");


    }

    public void assignImages(ImageView image, int i) {// 1.oyuncunun eline gelecek olan kartların resimlerini atadığımız değerlere göre eşleştiriyor


        Enum cardNumber = player1.get(i).cardNumber;
        Enum cardShape = player1.get(i).cardShape;


        if (cardNumber == CardNumber.Ace && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.as_hearts);
        } else if (cardNumber == CardNumber.Two && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.two_hearts);
        } else if (cardNumber == CardNumber.Three && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.three_hearts);
        } else if (cardNumber == CardNumber.Four && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.four_hearts);
        } else if (cardNumber == CardNumber.Five && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.five_hearts);
        } else if (cardNumber == CardNumber.Six && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.six_hearts);
        } else if (cardNumber == CardNumber.Seven && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.seven_hearts);
        } else if (cardNumber == CardNumber.Eight && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.eight_hearts);
        } else if (cardNumber == CardNumber.Nine && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.nine_hearts);
        } else if (cardNumber == CardNumber.Ten && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.ten_hearts);
        } else if (cardNumber == CardNumber.Jack && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.jack_herats);
        } else if (cardNumber == CardNumber.Queen && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.queen_hearts);
        } else if (cardNumber == CardNumber.King && cardShape == CardShape.Heart) {
            image.setImageResource(R.drawable.king_hearts);
        } else if (cardNumber == CardNumber.Ace && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.as_spades);
        } else if (cardNumber == CardNumber.Two && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.two_spades);
        } else if (cardNumber == CardNumber.Three && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.three_spades);
        } else if (cardNumber == CardNumber.Four && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.four_spades);
        } else if (cardNumber == CardNumber.Five && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.five_spades);
        } else if (cardNumber == CardNumber.Six && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.six_spades);
        } else if (cardNumber == CardNumber.Seven && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.seven_spades);
        } else if (cardNumber == CardNumber.Eight && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.eight_spades);
        } else if (cardNumber == CardNumber.Nine && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.nine_spades);
        } else if (cardNumber == CardNumber.Ten && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.ten_spades);
        } else if (cardNumber == CardNumber.Jack && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.jack_spades);
        } else if (cardNumber == CardNumber.Queen && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.queen_spades);
        } else if (cardNumber == CardNumber.King && cardShape == CardShape.Spades) {
            image.setImageResource(R.drawable.king_spades);
        } else if (cardNumber == CardNumber.Ace && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.as_diamonds);
        } else if (cardNumber == CardNumber.Two && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.two_diamonds);
        } else if (cardNumber == CardNumber.Three && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.three_diamonds);
        } else if (cardNumber == CardNumber.Four && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.foru_diamonds);
        } else if (cardNumber == CardNumber.Five && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.five_diamonds);
        } else if (cardNumber == CardNumber.Six && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.ssix_diamonds);
        } else if (cardNumber == CardNumber.Seven && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.seven_diamonds);
        } else if (cardNumber == CardNumber.Eight && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.eight_diamonds);
        } else if (cardNumber == CardNumber.Nine && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.nine_diamonds);
        } else if (cardNumber == CardNumber.Ten && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.ten_diamonds);
        } else if (cardNumber == CardNumber.Jack && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.jack_diamonds);
        } else if (cardNumber == CardNumber.Queen && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.queen_diamonds);
        } else if (cardNumber == CardNumber.King && cardShape == CardShape.Diamond) {
            image.setImageResource(R.drawable.king_diamonds);
        } else if (cardNumber == CardNumber.Ace && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.as_clubs);
        } else if (cardNumber == CardNumber.Two && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.two_clubs);
        } else if (cardNumber == CardNumber.Three && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.three_clubs);
        } else if (cardNumber == CardNumber.Four && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.four_clubs);
        } else if (cardNumber == CardNumber.Five && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.five_clubs);
        } else if (cardNumber == CardNumber.Six && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.six_clubs);
        } else if (cardNumber == CardNumber.Seven && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.seven_clubs);
        } else if (cardNumber == CardNumber.Eight && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.eight_clubs);
        } else if (cardNumber == CardNumber.Nine && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.nine_clubs);
        } else if (cardNumber == CardNumber.Ten && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.ten_clubs);
        } else if (cardNumber == CardNumber.Jack && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.jack_clubs);
        } else if (cardNumber == CardNumber.Queen && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.queen_clubs);
        } else if (cardNumber == CardNumber.King && cardShape == CardShape.Club) {
            image.setImageResource(R.drawable.king_clubs);
        }
    }

    public void assignImagesMiddle(ImageView image, int i) {//Ortaya atılacak ilk 4 kartın resimlerini atadığımız değerlere göre eşleştiriyor

        Enum cardNumber1 = playedCardDeck.get(i).cardNumber;
        Enum cardShape1 = playedCardDeck.get(i).cardShape;

        if (cardNumber1 == CardNumber.Ace && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.as_hearts);
        } else if (cardNumber1 == CardNumber.Two && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.two_hearts);
        } else if (cardNumber1 == CardNumber.Three && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.three_hearts);
        } else if (cardNumber1 == CardNumber.Four && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.four_hearts);
        } else if (cardNumber1 == CardNumber.Five && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.five_hearts);
        } else if (cardNumber1 == CardNumber.Six && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.six_hearts);
        } else if (cardNumber1 == CardNumber.Seven && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.seven_hearts);
        } else if (cardNumber1 == CardNumber.Eight && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.eight_hearts);
        } else if (cardNumber1 == CardNumber.Nine && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.nine_hearts);
        } else if (cardNumber1 == CardNumber.Ten && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.ten_hearts);
        } else if (cardNumber1 == CardNumber.Jack && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.jack_herats);
        } else if (cardNumber1 == CardNumber.Queen && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.queen_hearts);
        } else if (cardNumber1 == CardNumber.King && cardShape1 == CardShape.Heart) {
            image.setImageResource(R.drawable.king_hearts);
        } else if (cardNumber1 == CardNumber.Ace && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.as_spades);
        } else if (cardNumber1 == CardNumber.Two && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.two_spades);
        } else if (cardNumber1 == CardNumber.Three && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.three_spades);
        } else if (cardNumber1 == CardNumber.Four && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.four_spades);
        } else if (cardNumber1 == CardNumber.Five && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.five_spades);
        } else if (cardNumber1 == CardNumber.Six && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.six_spades);
        } else if (cardNumber1 == CardNumber.Seven && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.seven_spades);
        } else if (cardNumber1 == CardNumber.Eight && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.eight_spades);
        } else if (cardNumber1 == CardNumber.Nine && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.nine_spades);
        } else if (cardNumber1 == CardNumber.Ten && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.ten_spades);
        } else if (cardNumber1 == CardNumber.Jack && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.jack_spades);
        } else if (cardNumber1 == CardNumber.Queen && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.queen_spades);
        } else if (cardNumber1 == CardNumber.King && cardShape1 == CardShape.Spades) {
            image.setImageResource(R.drawable.king_spades);
        } else if (cardNumber1 == CardNumber.Ace && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.as_diamonds);
        } else if (cardNumber1 == CardNumber.Two && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.two_diamonds);
        } else if (cardNumber1 == CardNumber.Three && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.three_diamonds);
        } else if (cardNumber1 == CardNumber.Four && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.foru_diamonds);
        } else if (cardNumber1 == CardNumber.Five && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.five_diamonds);
        } else if (cardNumber1 == CardNumber.Six && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.ssix_diamonds);
        } else if (cardNumber1 == CardNumber.Seven && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.seven_diamonds);
        } else if (cardNumber1 == CardNumber.Eight && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.eight_diamonds);
        } else if (cardNumber1 == CardNumber.Nine && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.nine_diamonds);
        } else if (cardNumber1 == CardNumber.Ten && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.ten_diamonds);
        } else if (cardNumber1 == CardNumber.Jack && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.jack_diamonds);
        } else if (cardNumber1 == CardNumber.Queen && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.queen_diamonds);
        } else if (cardNumber1 == CardNumber.King && cardShape1 == CardShape.Diamond) {
            image.setImageResource(R.drawable.king_diamonds);
        } else if (cardNumber1 == CardNumber.Ace && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.as_clubs);
        } else if (cardNumber1 == CardNumber.Two && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.two_clubs);
        } else if (cardNumber1 == CardNumber.Three && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.three_clubs);
        } else if (cardNumber1 == CardNumber.Four && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.four_clubs);
        } else if (cardNumber1 == CardNumber.Five && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.five_clubs);
        } else if (cardNumber1 == CardNumber.Six && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.six_clubs);
        } else if (cardNumber1 == CardNumber.Seven && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.seven_clubs);
        } else if (cardNumber1 == CardNumber.Eight && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.eight_clubs);
        } else if (cardNumber1 == CardNumber.Nine && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.nine_clubs);
        } else if (cardNumber1 == CardNumber.Ten && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.ten_clubs);
        } else if (cardNumber1 == CardNumber.Jack && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.jack_clubs);
        } else if (cardNumber1 == CardNumber.Queen && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.queen_clubs);
        } else if (cardNumber1 == CardNumber.King && cardShape1 == CardShape.Club) {
            image.setImageResource(R.drawable.king_clubs);
        }

    }

    public void assignImagesCPU(ImageView image, int i) {//CPU ya dağıtılacak ilk 4 kartın resimlerini atadığımız değerlere göre eşleştiriyor
        Enum cardNumber2 = player2.get(i).cardNumber;
        Enum cardShape2 = player2.get(i).cardShape;

        if (cardNumber2 == CardNumber.Ace && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.as_hearts);
        } else if (cardNumber2 == CardNumber.Two && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.two_hearts);
        } else if (cardNumber2 == CardNumber.Three && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.three_hearts);
        } else if (cardNumber2 == CardNumber.Four && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.four_hearts);
        } else if (cardNumber2 == CardNumber.Five && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.five_hearts);
        } else if (cardNumber2 == CardNumber.Six && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.six_hearts);
        } else if (cardNumber2 == CardNumber.Seven && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.seven_hearts);
        } else if (cardNumber2 == CardNumber.Eight && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.eight_hearts);
        } else if (cardNumber2 == CardNumber.Nine && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.nine_hearts);
        } else if (cardNumber2 == CardNumber.Ten && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.ten_hearts);
        } else if (cardNumber2 == CardNumber.Jack && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.jack_herats);
        } else if (cardNumber2 == CardNumber.Queen && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.queen_hearts);
        } else if (cardNumber2 == CardNumber.King && cardShape2 == CardShape.Heart) {
            image.setImageResource(R.drawable.king_hearts);
        } else if (cardNumber2 == CardNumber.Ace && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.as_spades);
        } else if (cardNumber2 == CardNumber.Two && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.two_spades);
        } else if (cardNumber2 == CardNumber.Three && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.three_spades);
        } else if (cardNumber2 == CardNumber.Four && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.four_spades);
        } else if (cardNumber2 == CardNumber.Five && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.five_spades);
        } else if (cardNumber2 == CardNumber.Six && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.six_spades);
        } else if (cardNumber2 == CardNumber.Seven && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.seven_spades);
        } else if (cardNumber2 == CardNumber.Eight && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.eight_spades);
        } else if (cardNumber2 == CardNumber.Nine && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.nine_spades);
        } else if (cardNumber2 == CardNumber.Ten && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.ten_spades);
        } else if (cardNumber2 == CardNumber.Jack && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.jack_spades);
        } else if (cardNumber2 == CardNumber.Queen && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.queen_spades);
        } else if (cardNumber2 == CardNumber.King && cardShape2 == CardShape.Spades) {
            image.setImageResource(R.drawable.king_spades);
        } else if (cardNumber2 == CardNumber.Ace && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.as_diamonds);
        } else if (cardNumber2 == CardNumber.Two && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.two_diamonds);
        } else if (cardNumber2 == CardNumber.Three && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.three_diamonds);
        } else if (cardNumber2 == CardNumber.Four && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.foru_diamonds);
        } else if (cardNumber2 == CardNumber.Five && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.five_diamonds);
        } else if (cardNumber2 == CardNumber.Six && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.ssix_diamonds);
        } else if (cardNumber2 == CardNumber.Seven && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.seven_diamonds);
        } else if (cardNumber2 == CardNumber.Eight && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.eight_diamonds);
        } else if (cardNumber2 == CardNumber.Nine && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.nine_diamonds);
        } else if (cardNumber2 == CardNumber.Ten && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.ten_diamonds);
        } else if (cardNumber2 == CardNumber.Jack && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.jack_diamonds);
        } else if (cardNumber2 == CardNumber.Queen && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.queen_diamonds);
        } else if (cardNumber2 == CardNumber.King && cardShape2 == CardShape.Diamond) {
            image.setImageResource(R.drawable.king_diamonds);
        } else if (cardNumber2 == CardNumber.Ace && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.as_clubs);
        } else if (cardNumber2 == CardNumber.Two && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.two_clubs);
        } else if (cardNumber2 == CardNumber.Three && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.three_clubs);
        } else if (cardNumber2 == CardNumber.Four && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.four_clubs);
        } else if (cardNumber2 == CardNumber.Five && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.five_clubs);
        } else if (cardNumber2 == CardNumber.Six && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.six_clubs);
        } else if (cardNumber2 == CardNumber.Seven && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.seven_clubs);
        } else if (cardNumber2 == CardNumber.Eight && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.eight_clubs);
        } else if (cardNumber2 == CardNumber.Nine && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.nine_clubs);
        } else if (cardNumber2 == CardNumber.Ten && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.ten_clubs);
        } else if (cardNumber2 == CardNumber.Jack && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.jack_clubs);
        } else if (cardNumber2 == CardNumber.Queen && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.queen_clubs);
        } else if (cardNumber2 == CardNumber.King && cardShape2 == CardShape.Club) {
            image.setImageResource(R.drawable.king_clubs);
        }

    }

    @Override
    public void onClick(View view) {//Ortaya kart atma
        int player1CardCount = 0;//1.oyuncunun destesindeki kart sayısını elimizde tutacağız

        switch (view.getId()) {
            case R.id.p_c0:
                binding.animLayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideOutUp).duration(700).repeat(0).playOn(binding.animDefault);
                img = binding.pC0.getDrawable();
                binding.animDefault.setImageDrawable(img);
                binding.playedCardDeckImage.setImageDrawable(img);
                binding.pC0.setImageResource(0);//Resmi kaldırdım
                playedCardDeck.add(player1.get(0));//Orta desteye seçilen kartı ekledim
                memory.add(player1.get(0));//Seçilen kartı memorye ekledik


                for (int j = 0; j < 4; j++) {
                    if (player1.get(j).cardNumber != null) {//değeri null olan kartları saymıyor
                        player1CardCount++;
                    }
                }

                if (playedCardDeck.size() == 2 && player1CardCount == player2.size() && player1.get(0).cardNumber.getNumVal() == playedCardDeck.get(0).cardNumber.getNumVal()) {//Player1 pişti yaparsa
                    player1Score = player1Score + playedCardDeck.size();
                    player1Score += 10;
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık ( kart sayısı yani 2 kart ve 10 ekstra puan ekliyoruz)
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player 1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                if (playedCardDeck.size() != 0 && player1.get(0).cardNumber.getNumVal() == 11) {//Player1 Vale atarsa gerçekleşmesi gerekenler
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }
                if (playedCardDeck.size() != 0 && playedCardDeck.size() != 1 && player1.get(0).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 2).cardNumber.getNumVal()) {//Player 1 ortadakiyle aynı değeri atarsa
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                player1.set(0, bosKart);//Kaldırdığımız elementin yerini boş kart ile doldurdum


                if (player1.get(0) == bosKart) {//Kart ortaya gittikten sonra boş elemanı ortaya atmamak için ınvisible yaptım
                    binding.pC0.setVisibility(View.INVISIBLE);
                }

                //cpuPlay();
                break;

            case R.id.p_c1:
                binding.animLayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideOutUp).duration(700).repeat(0).playOn(binding.animDefault);
                img = binding.pC1.getDrawable();
                binding.animDefault.setImageDrawable(img);
                binding.playedCardDeckImage.setImageDrawable(img);
                binding.pC1.setImageResource(0);//Resmi kaldırdım
                playedCardDeck.add(player1.get(1));//Orta desteye seçilen kartı ekledim
                memory.add(player1.get(1));//Seçilen kartı memorye ekledik

                for (int j = 0; j < 4; j++) {
                    if (player1.get(j).cardNumber != null) {//değeri null olan kartları saymıyor
                        player1CardCount++;
                    }
                }

                if (playedCardDeck.size() == 2 && player1CardCount == player2.size() && player1.get(1).cardNumber.getNumVal() == playedCardDeck.get(0).cardNumber.getNumVal()) {//Player1 pişti yaparsa
                    player1Score = player1Score + playedCardDeck.size();
                    player1Score += 10;
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık ( kart sayısı yani 2 kart ve 10 ekstra puan ekliyoruz)
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                if (playedCardDeck.size() != 0 && player1.get(1).cardNumber.getNumVal() == 11) {//Player1 Vale atarsa gerçekleşmesi gerekenler
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }
                if (playedCardDeck.size() != 0 && playedCardDeck.size() != 1 && player1.get(1).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 2).cardNumber.getNumVal()) {//Player 1 ortadakiyle aynı değeri atarsa
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                player1.set(1, bosKart);//Kaldırdığımız elementin yerini boş kart ile doldurdum

                if (player1.get(1) == bosKart) {//Kart ortaya gittikten sonra boş elemanı ortaya atmamak için ınvisible yaptım
                    binding.pC1.setVisibility(View.INVISIBLE);
                }


                //cpuPlay();
                break;

            case R.id.p_c2:
                binding.animLayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideOutUp).duration(700).repeat(0).playOn(binding.animDefault);
                img = binding.pC2.getDrawable();
                binding.animDefault.setImageDrawable(img);
                binding.playedCardDeckImage.setImageDrawable(img);
                binding.pC2.setImageResource(0);//Resmi kaldırdım
                playedCardDeck.add(player1.get(2));//Orta desteye seçilen kartı ekledim
                memory.add(player1.get(2));//Seçilen kartı memorye ekledik

                for (int j = 0; j < 4; j++) {
                    if (player1.get(j).cardNumber != null) {//değeri null olan kartları saymıyor
                        player1CardCount++;
                    }
                }

                if (playedCardDeck.size() == 2 && player1CardCount == player2.size() && player1.get(2).cardNumber.getNumVal() == playedCardDeck.get(0).cardNumber.getNumVal()) {//Player1 pişti yaparsa
                    player1Score = player1Score + playedCardDeck.size();
                    player1Score += 10;
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık ( kart sayısı yani 2 kart ve 10 ekstra puan ekliyoruz)
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                if (playedCardDeck.size() != 0 && player1.get(2).cardNumber.getNumVal() == 11) {//Player1 Vale atarsa gerçekleşmesi gerekenler
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }
                if (playedCardDeck.size() != 0 && playedCardDeck.size() != 1 && player1.get(2).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 2).cardNumber.getNumVal()) {//Player 1 ortadakiyle aynı değeri atarsa
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }

                player1.set(2, bosKart);//Kaldırdığımız elementin yerini boş kart ile doldurdum

                if (player1.get(2) == bosKart) {//Kart ortaya gittikten sonra boş elemanı ortaya atmamak için ınvisible yaptım
                    binding.pC2.setVisibility(View.INVISIBLE);
                }


                //cpuPlay();
                break;

            case R.id.p_c3:
                binding.animLayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideOutUp).duration(700).repeat(0).playOn(binding.animDefault);
                img = binding.pC3.getDrawable();
                binding.animDefault.setImageDrawable(img);
                binding.playedCardDeckImage.setImageDrawable(img);
                binding.pC3.setImageResource(0);//Resmi kaldırdım
                playedCardDeck.add(player1.get(3));//Orta desteye seçilen kartı ekledim
                memory.add(player1.get(3));//Seçilen kartı memorye ekledik

                for (int j = 0; j < 4; j++) {
                    if (player1.get(j).cardNumber != null) {//değeri null olan kartları saymıyor
                        player1CardCount++;
                    }
                }

                if (playedCardDeck.size() == 2 && player1CardCount == player2.size() && player1.get(3).cardNumber.getNumVal() == playedCardDeck.get(0).cardNumber.getNumVal()) {//Player1 pişti yaparsa
                    player1Score = player1Score + playedCardDeck.size();
                    player1Score += 10;
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık ( kart sayısı yani 2 kart ve 10 ekstra puan ekliyoruz)
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//CPU nu nkazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }


                if (playedCardDeck.size() != 0 && player1.get(3).cardNumber.getNumVal() == 11) {//Player1 Vale atarsa gerçekleşmesi gerekenler

                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in nkazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);


                }
                if (playedCardDeck.size() != 0 && playedCardDeck.size() != 1 && player1.get(3).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 2).cardNumber.getNumVal()) {//Player 1 ortadakiyle aynı değeri atarsa
                    player1Score = player1Score + playedCardDeck.size();
                    String myScore = String.valueOf(player1Score);//Bu bölümde player 1 in skorunu arttırdık
                    binding.playerScore.setText("My \n Score:" + myScore);

                    player1Accumulated.addAll(playedCardDeck);//Player1 in kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutUpRight).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);

                }


                player1.set(3, bosKart);//Kaldırdığımız elementin yerini boş kart ile doldurdum

                if (player1.get(3) == bosKart) {//Kart ortaya gittikten sonra boş elemanı ortaya atmamak için ınvisible yaptım
                    binding.pC3.setVisibility(View.INVISIBLE);
                }

                //cpuPlay();
                break;

        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> cpuPlay(), 1000);


    }


    public void cpuPlay() { //CPU oynuyor
        int e = getRandomNumber(0, 4);//Default deste ile alakalı

        int player1CardCount = 0;//1.oyuncunun destesindeki kart sayısını elimizde tutacağız

        for (int j = 0; j < 4; j++) {
            if (player1.get(j).cardNumber != null) {//değeri null olan kartları saymıyor
                player1CardCount++;
            }
        }

        if (player2.size() > player1CardCount) {//eğer CPU'nun elindeki kart sayısı 1. oyuncudan fazla ise

            if (playedCardDeck.size() != 0 && player2.get(ifSame(player2)).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal()) {
                int realImg = ifSame(player2);

                if (player2.size() == 3 && (ifSame(player2) == 0 || ifSame(player2) == 1 || ifSame(player2) == 2) && imageList2.get(0).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 3 && (ifSame(player2) == 1 || ifSame(player2) == 2) && imageList2.get(1).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 3 && ifSame(player2) == 2 && imageList2.get(2).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 2 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && ifSame(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && ifSame(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && ifSame(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && ifSame(player2) == 1 && (imageList2.get(1).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && ifSame(player2) == 1 && (imageList2.get(1).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 1 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 3;
                } else if (player2.size() == 1 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 1 && ifSame(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                }


                imgCPU = imageList2.get(realImg).getDrawable();
                imageList2.get(realImg).setImageDrawable(null);
                binding.cpuanimDefault.setImageDrawable(imgCPU);
                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                binding.playedCardDeckImage.setImageDrawable(imgCPU);

                playedCardDeck.add(player2.get(ifSame(player2)));
                memory.add(player2.get(ifSame(player2)));//Memorye ekledik
                player2.remove(player2.get(ifSame(player2)));

                if (playedCardDeck.size() == 2 && playedCardDeck.get(1).cardNumber.getNumVal() == playedCardDeck.get(0).cardNumber.getNumVal()) {
                    player2Score += playedCardDeck.size();
                    player2Score = player2Score + 10;
                    String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                    binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                    player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);
                } else {
                    player2Score += playedCardDeck.size();
                    String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                    binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                    player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);
                }


                if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                    imageDefault.get(e).setImageDrawable(null);
                    imageDefault.get(e).setImageResource(0);
                } else if (imageDefault.get(e).getDrawable() == null) {

                    imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);
                }


                if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyunu bittikten sonra olacak işlemler...
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Game Over");
                    if (player1Score > player2Score) {
                        alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                    } else if (player2Score > player1Score) {
                        alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                    } else {
                        alert.setMessage("DRAW!!!");
                    }
                    alert.setCancelable(false);

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    alert.show();

                } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                    player1 = Card.dealer(getAllCard);
                    player2 = Card.dealer(getAllCard);

                    for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                        assignImages(imageList.get(n), n);
                        binding.pC0.setVisibility(View.VISIBLE);
                        binding.pC1.setVisibility(View.VISIBLE);
                        binding.pC2.setVisibility(View.VISIBLE);
                        binding.pC3.setVisibility(View.VISIBLE);
                    }

                    for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile
                        assignImagesCPU(imageList2.get(m), m);
                    }

                    for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                        binding.default0.setImageResource(imgDefault);
                        binding.default1.setImageResource(imgDefault);
                        binding.default2.setImageResource(imgDefault);
                        binding.default3.setImageResource(imgDefault);
                    }
                    binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                }

            } else if (valueCount(player2) >= 3) {//Eğer CPU kart saydıysa yapacağı işlemler
                int realImg = valueIndex(player2);

                if (player2.size() == 3 && (valueIndex(player2) == 0 || valueIndex(player2) == 1 || valueIndex(player2) == 2) && imageList2.get(0).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 3 && (valueIndex(player2) == 1 || valueIndex(player2) == 2) && imageList2.get(1).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 3 && valueIndex(player2) == 2 && imageList2.get(2).getDrawable() == null) {
                    realImg += 1;
                } else if (player2.size() == 2 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && valueIndex(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && valueIndex(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && valueIndex(player2) == 1 && (imageList2.get(0).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 2 && valueIndex(player2) == 1 && (imageList2.get(1).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 2 && valueIndex(player2) == 1 && (imageList2.get(1).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                } else if (player2.size() == 1 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null && imageList2.get(2).getDrawable() == null)) {
                    realImg += 3;
                } else if (player2.size() == 1 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(1).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 2;
                } else if (player2.size() == 1 && valueIndex(player2) == 0 && (imageList2.get(0).getDrawable() == null && imageList2.get(2).getDrawable() == null && imageList2.get(3).getDrawable() == null)) {
                    realImg += 1;
                }


                imgCPU = imageList2.get(realImg).getDrawable();
                imageList2.get(realImg).setImageDrawable(null);
                binding.cpuanimDefault.setImageDrawable(imgCPU);
                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                binding.playedCardDeckImage.setImageDrawable(imgCPU);

                playedCardDeck.add(player2.get(valueIndex(player2)));
                memory.add(player2.get(valueIndex(player2)));//Memorye ekledik
                player2.remove(player2.get(valueIndex(player2)));

                if (playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal() == 11) {
                    player2Score += playedCardDeck.size();
                    String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                    binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                    player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                    playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                    YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                    fadeImg = binding.playedCardDeckImage.getDrawable();
                    binding.fadeAnimDefault.setImageDrawable(fadeImg);

                    binding.playedCardDeckImage.setImageResource(0);
                }


                if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                    imageDefault.get(e).setImageDrawable(null);
                    imageDefault.get(e).setImageResource(0);
                } else if (imageDefault.get(e).getDrawable() == null) {

                    imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);
                }


                if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyunu bittikten sonra olacak işlemler...
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Game Over");
                    if (player1Score > player2Score) {
                        alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                    } else if (player2Score > player1Score) {
                        alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                    } else {
                        alert.setMessage("DRAW!!!");
                    }
                    alert.setCancelable(false);

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    alert.show();

                } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                    player1 = Card.dealer(getAllCard);
                    player2 = Card.dealer(getAllCard);

                    for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                        assignImages(imageList.get(n), n);
                        binding.pC0.setVisibility(View.VISIBLE);
                        binding.pC1.setVisibility(View.VISIBLE);
                        binding.pC2.setVisibility(View.VISIBLE);
                        binding.pC3.setVisibility(View.VISIBLE);
                    }

                    for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile

                        assignImagesCPU(imageList2.get(m), m);
                    }

                    for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                        binding.default0.setImageResource(imgDefault);
                        binding.default1.setImageResource(imgDefault);
                        binding.default2.setImageResource(imgDefault);
                        binding.default3.setImageResource(imgDefault);
                    }

                    binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                }

            } else {
                for (int i = 0; i < player2.size(); i++) {
                    if (playedCardDeck.size() == 1 && player2.get(i).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal()) {//Pişti algoritması
                        playedCardDeck.add(player2.get(i));
                        memory.add(player2.get(i));//Memorye ekledik
                        player2.remove(i);

                        for (int CpuImg = 0; CpuImg < imageList2.size(); CpuImg++) {
                            if (imageList2.get(CpuImg).getDrawable() != null) {
                                imgCPU = imageList2.get(CpuImg).getDrawable();
                                imageList2.get(CpuImg).setImageDrawable(null);
                                binding.cpuanimDefault.setImageDrawable(imgCPU);
                                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                                binding.playedCardDeckImage.setImageDrawable(imgCPU);
                                break;
                            }
                        }

                        player2Score += playedCardDeck.size();
                        player2Score = player2Score + 10;
                        String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                        binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                        player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                        playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                        YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                        fadeImg = binding.playedCardDeckImage.getDrawable();
                        binding.fadeAnimDefault.setImageDrawable(fadeImg);


                        if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                            imageDefault.get(e).setImageDrawable(null);
                            imageDefault.get(e).setImageResource(0);
                        } else if (imageDefault.get(e).getDrawable() == null) {

                            imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);
                        }


                        binding.playedCardDeckImage.setImageResource(0);


                        if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyunu bittikten sonra olacak işlemler...
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Game Over");
                            if (player1Score > player2Score) {
                                alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                            } else if (player2Score > player1Score) {
                                alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                            } else {
                                alert.setMessage("DRAW!!!");
                            }
                            alert.setCancelable(false);

                            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }
                            });
                            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alert.show();

                        } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                            player1 = Card.dealer(getAllCard);
                            player2 = Card.dealer(getAllCard);

                            for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                                assignImages(imageList.get(n), n);
                                binding.pC0.setVisibility(View.VISIBLE);
                                binding.pC1.setVisibility(View.VISIBLE);
                                binding.pC2.setVisibility(View.VISIBLE);
                                binding.pC3.setVisibility(View.VISIBLE);
                            }

                            for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile
                                assignImagesCPU(imageList2.get(m), m);
                            }

                            for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                                binding.default0.setImageResource(imgDefault);
                                binding.default1.setImageResource(imgDefault);
                                binding.default2.setImageResource(imgDefault);
                                binding.default3.setImageResource(imgDefault);
                            }
                            binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                        }
                        break;

                    } else if (playedCardDeck.size() != 0 && player2.get(i).cardNumber.getNumVal() == 11) {//Eğer Vale gelirse ortadaki bütün kartları toplaması gereken algoritma
                        playedCardDeck.add(player2.get(i));
                        memory.add(player2.get(i));//Memorye ekledik
                        player2.remove(i);

                        for (int CpuImg = 0; CpuImg < imageList2.size(); CpuImg++) {
                            if (imageList2.get(CpuImg).getDrawable() != null) {
                                imgCPU = imageList2.get(CpuImg).getDrawable();
                                imageList2.get(CpuImg).setImageDrawable(null);
                                binding.cpuanimDefault.setImageDrawable(imgCPU);
                                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                                binding.playedCardDeckImage.setImageDrawable(imgCPU);
                                break;
                            }
                        }

                        player2Score += playedCardDeck.size();
                        String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                        binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                        player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                        playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                        YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                        fadeImg = binding.playedCardDeckImage.getDrawable();
                        binding.fadeAnimDefault.setImageDrawable(fadeImg);


                        if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                            imageDefault.get(e).setImageDrawable(null);
                            imageDefault.get(e).setImageResource(0);
                        } else if (imageDefault.get(e).getDrawable() == null) {

                            imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);
                        }


                        binding.playedCardDeckImage.setImageResource(0);


                        if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyun bittikten sonra olacak işlemler...
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Game Over");
                            if (player1Score > player2Score) {
                                alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                            } else if (player2Score > player1Score) {
                                alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                            } else {
                                alert.setMessage("DRAW!!!");
                            }
                            alert.setCancelable(false);

                            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }
                            });
                            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alert.show();

                        } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                            player1 = Card.dealer(getAllCard);
                            player2 = Card.dealer(getAllCard);

                            for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                                assignImages(imageList.get(n), n);
                                binding.pC0.setVisibility(View.VISIBLE);
                                binding.pC1.setVisibility(View.VISIBLE);
                                binding.pC2.setVisibility(View.VISIBLE);
                                binding.pC3.setVisibility(View.VISIBLE);
                            }

                            for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile
                                assignImagesCPU(imageList2.get(m), m);
                            }

                            for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                                binding.default0.setImageResource(imgDefault);
                                binding.default1.setImageResource(imgDefault);
                                binding.default2.setImageResource(imgDefault);
                                binding.default3.setImageResource(imgDefault);
                            }
                            binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                        }

                        break;

                    } else if (playedCardDeck.size() != 0 && player2.get(i).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal()) {//Atılan kağıtlar eşitse
                        playedCardDeck.add(player2.get(i));
                        memory.add(player2.get(i));//Memorye ekledik
                        player2.remove(i);

                        for (int CpuImg = 0; CpuImg < imageList2.size(); CpuImg++) {
                            if (imageList2.get(CpuImg).getDrawable() != null) {
                                imgCPU = imageList2.get(CpuImg).getDrawable();
                                imageList2.get(CpuImg).setImageDrawable(null);
                                binding.cpuanimDefault.setImageDrawable(imgCPU);
                                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                                binding.playedCardDeckImage.setImageDrawable(imgCPU);
                                break;
                            }
                        }


                        player2Score += playedCardDeck.size();
                        String opponentScore = String.valueOf(player2Score);//Bu bölümde CPU nun skorunu arttırdık
                        binding.opponentScore.setText("CPU \n Score:" + opponentScore);

                        player2Accumulated.addAll(playedCardDeck);//CPU nun kazandığı kartları kazandıkları destesine atıyoruz
                        playedCardDeck.removeAll(playedCardDeck);//Ortadaki kartları temizliyoruz

                        YoYo.with(Techniques.RotateOutDownLeft).duration(700).repeat(0).playOn(binding.fadeAnimDefault);
                        fadeImg = binding.playedCardDeckImage.getDrawable();
                        binding.fadeAnimDefault.setImageDrawable(fadeImg);


                        if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                            imageDefault.get(e).setImageDrawable(null);
                            imageDefault.get(e).setImageResource(0);
                        } else if (imageDefault.get(e).getDrawable() == null) {

                            imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);

                        }


                        binding.playedCardDeckImage.setImageResource(0);


                        if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyunu bittikten sonra olacak işlemler...
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Game Over");
                            if (player1Score > player2Score) {
                                alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                            } else if (player2Score > player1Score) {
                                alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                            } else {
                                alert.setMessage("DRAW!!!");
                            }
                            alert.setCancelable(false);

                            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }
                            });
                            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alert.show();

                        } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                            player1 = Card.dealer(getAllCard);
                            player2 = Card.dealer(getAllCard);

                            for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                                assignImages(imageList.get(n), n);
                                binding.pC0.setVisibility(View.VISIBLE);
                                binding.pC1.setVisibility(View.VISIBLE);
                                binding.pC2.setVisibility(View.VISIBLE);
                                binding.pC3.setVisibility(View.VISIBLE);
                            }

                            for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile
                                assignImagesCPU(imageList2.get(m), m);
                            }

                            for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                                binding.default0.setImageResource(imgDefault);
                                binding.default1.setImageResource(imgDefault);
                                binding.default2.setImageResource(imgDefault);
                                binding.default3.setImageResource(imgDefault);
                            }
                            binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                        }

                        break;

                    } else if (playedCardDeck.size() == 0 || player2.get(i).cardNumber.getNumVal() > playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal() || player2.get(i).cardNumber.getNumVal() < playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal()) {//eğer (CPU için) elindeki kart ortaya atılan karttan büyüksede küçüksede kartını oynasın
                        playedCardDeck.add(player2.get(i));
                        memory.add(player2.get(i));//Memorye ekledik
                        player2.remove(i);


                        for (int CpuImg = 0; CpuImg < imageList2.size(); CpuImg++) {
                            if (imageList2.get(CpuImg).getDrawable() != null) {
                                imgCPU = imageList2.get(CpuImg).getDrawable();
                                imageList2.get(CpuImg).setImageDrawable(null);
                                binding.cpuanimDefault.setImageDrawable(imgCPU);
                                YoYo.with(Techniques.SlideOutDown).duration(700).repeat(0).playOn(binding.cpuanimDefault);
                                binding.playedCardDeckImage.setImageDrawable(imgCPU);
                                break;
                            }
                        }

                        if (imageDefault.get(e).getDrawable() != null) {//Karşıdakinin default destesinden rastgele kart silme işlemi
                            imageDefault.get(e).setImageDrawable(null);
                            imageDefault.get(e).setImageResource(0);
                        } else if (imageDefault.get(e).getDrawable() == null) {

                            imageDefault.get(nonNull(imageDefault)).setImageDrawable(null);
                        }


                        if (getAllCard.size() == 0 && player1.size() == 4 && player2.size() == 0) {//Eğer deste bittiyse oyunu bittikten sonra olacak işlemler...
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Game Over");
                            if (player1Score > player2Score) {
                                alert.setMessage("By " + player1Score + " points Player1 won. Would you like to restart game ? ");
                            } else if (player2Score > player1Score) {
                                alert.setMessage("By " + player2Score + " points CPU won. Would you like to restart game ? ");
                            } else {
                                alert.setMessage("DRAW!!!");
                            }
                            alert.setCancelable(false);

                            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }
                            });
                            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alert.show();

                        } else if (player1.size() == 4 && player2.size() == 0) {// İki oyuncununda elindeki kartları bittikten sonra kart dağıtımı
                            player1 = Card.dealer(getAllCard);
                            player2 = Card.dealer(getAllCard);

                            for (int n = 0; n < 4; n++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz birinci oyuncunun destesi ile
                                assignImages(imageList.get(n), n);
                                binding.pC0.setVisibility(View.VISIBLE);
                                binding.pC1.setVisibility(View.VISIBLE);
                                binding.pC2.setVisibility(View.VISIBLE);
                                binding.pC3.setVisibility(View.VISIBLE);
                            }

                            for (int m = 0; m < 4; m++) {//Dağıttığımız kartların resimlerini eşleştiriyoruz CPU' nun destesi ile

                                assignImagesCPU(imageList2.get(m), m);
                            }

                            for (int defNew = 0; defNew < 4; defNew++) {//El bittikten sonra oluşturduğumuz yeni default deck resimleri
                                binding.default0.setImageResource(imgDefault);
                                binding.default1.setImageResource(imgDefault);
                                binding.default2.setImageResource(imgDefault);
                                binding.default3.setImageResource(imgDefault);
                            }

                            binding.cardLeftTxt.setText(getAllCard.size() + " Card Left");
                        }
                        break;

                    }


                }
            }
        }


    }

    public int ifSame(List<Card> array) { //Eğer ortadaki kartla aynı kart varsa CPU nun kendi destedesinde atması gerektiği kartın indexini döndürüyor
        if (array.size() == 0) {
            return -1; //Eğer listede hiç eleman yoksa
        }
        int pos = 0;
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).cardNumber.getNumVal() == playedCardDeck.get(playedCardDeck.size() - 1).cardNumber.getNumVal()) {
                pos = i;
            }
        }
        return pos;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int nonNull(List<ImageView> array) {//Default destede kart silmek için gereken fonksyon
        int pos = 0;
        for (int i = 0; i < imageDefault.size(); i++) {
            if (array.get(i).getDrawable() != null) {
                pos = i;
            }
        }
        return pos;
    }

    public int valueCount(List<Card> array) {//Destedeki kart sayısını sayması için oluşturduğumuz fonksyon
        int j;
        int i;
        if (array.size() == 0) {
            return -1;
        }
        int count = 0;
        for (j = 0; j < array.size(); j++) {
            for (i = 0; i < memory.size(); i++) {
                if (memory.get(i).cardNumber.getNumVal() == array.get(j).cardNumber.getNumVal()) {
                    count++;
                }
            }
            if (count <= 2) {
                count = 0;
            } else {
                break;
            }
        }

        return count;
    }

    public int valueIndex(List<Card> array) {//Destedeki kart sayısını sayması ve o kartın indexini dönmesi için oluşturduğumuz fonksyon
        int j;
        int i;
        if (array.size() == 0) {
            return -1;
        }
        int count = 0;
        for (j = 0; j < array.size(); j++) {
            for (i = 0; i < memory.size(); i++) {
                if (memory.get(i).cardNumber.getNumVal() == array.get(j).cardNumber.getNumVal()) {
                    count++;

                }
            }
            if (count <= 2) {
                count = 0;
            } else {
                break;
            }
        }

        return j;
    }


}
