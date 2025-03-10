package OOP.BE.datascraping.dataloader;

import OOP.BE.datascraping.dataloader.blog.JsonLoaderBlog;
import OOP.BE.datascraping.dataloader.nftcollection.JsonLoaderNFTCollection;
import OOP.BE.datascraping.dataloader.twitter.JsonLoaderTwitter;
import OOP.BE.datascraping.model.Entity;
import OOP.BE.datascraping.model.blog.Blog;
import OOP.BE.datascraping.model.blog.BlogEntity;
import OOP.BE.datascraping.model.twitter.TwitterEntity;

import java.util.*;
import java.util.stream.Collectors;

import static OOP.BE.datascraping.utils.BlogTagFrequency.calculateTagFrequency;

public class EntitiesGenerator {
    private DataLoader[] loaders;
    public EntitiesGenerator(){
        loaders = new DataLoader[]{
            new JsonLoaderTwitter("twitter.json"),
            new JsonLoaderNFTCollection("binance1d.json"),
            new JsonLoaderNFTCollection("binance7d.json"),
            new JsonLoaderNFTCollection("opensea1d.json"),
            new JsonLoaderNFTCollection("opensea7d.json"),
            new JsonLoaderNFTCollection("rarible1d.json"),
            new JsonLoaderNFTCollection("rarible7d.json"),
            new JsonLoaderNFTCollection("niftygateway1d.json"),
            new JsonLoaderNFTCollection("niftygateway7d.json"),
            new JsonLoaderBlog("nftplazas.json"),
            new JsonLoaderBlog("newbitcoins.json"),
            new JsonLoaderBlog("cointelegraph.json")
        };
    }


    public Map<String, Collection<Entity>> generate(){
        Map<String, Collection<Entity>> data = new HashMap<>();

        for(DataLoader dataLoader : loaders){
            int dem = 0;
            Collection<Entity> entities = dataLoader.load();
            String loaderClassName = dataLoader.getClass().getSimpleName();
            String label = loaderClassName.substring(10);
            data.put(label, entities);

            for(Entity x : entities){
                dem++;
            }
            System.out.println("Thuc the: " + label + " co tong so luong la: " + dem);
        }
        return data;
    }

    public static void main(String[] args) {
        Map<String, Collection<Entity>> data =   new EntitiesGenerator().generate();
        Collection<Entity> blogs =  data.get("Blog");
        Collection<Entity> twits =  data.get("Twitter");

        // thay key NFTCollection bang Twitter hoac Blog de lay du lieu tuong ung
        Map<String, Integer> tagFrequencyMap = calculateTagFrequency(blogs);
        Map<String, Integer> tagFrequencyMap1 = calculateTagFrequency(twits);
        // Print the result
        int cnt=0;
        for (Map.Entry<String, Integer> entry : tagFrequencyMap1.entrySet()) {
            ++cnt;
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("So luong tag: "+cnt);


    }
}
