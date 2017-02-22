package fr.goui.riskgameofthroneshelperv2.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

/**
 * Adapter for the list of players.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> implements Observer {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<Player> mListOfPlayers;

    private PlayerModel mPlayerModel = PlayerModel.getInstance();

    public PlayerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mListOfPlayers = PlayerModel.getInstance().getPlayers();
        mPlayerModel.addObserver(this);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayerViewHolder(mLayoutInflater.inflate(R.layout.item_player, parent, false));
    }

    @Override
    public void onBindViewHolder(final PlayerViewHolder holder, int position) {
        final Player player = mListOfPlayers.get(position);
        if (player != null) {
            holder.playerView.setBackgroundColor(getColor(player.getColorIndex()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListOfPlayers.size() < 7) { // preventing picking color when max players
                        int oldColorIndex = player.getColorIndex();
                        holder.playerView.setBackgroundColor(getColor(mPlayerModel.getNextAvailableColorIndex(player)));
                    }
                }
            });
        }
    }

    /**
     * Gets a color from the player_color xml resource.
     *
     * @param position the color position
     * @return the color's resource id
     */
    private int getColor(int position) {
        TypedArray ta = mContext.getResources().obtainTypedArray(R.array.player_color);
        int color = ta.getColor(position, -1);
        ta.recycle();
        return color;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof PlayerModel) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mListOfPlayers.size();
    }

    /**
     * View holder for the player item.
     */
    static class PlayerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_view)
        View playerView;

        PlayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
