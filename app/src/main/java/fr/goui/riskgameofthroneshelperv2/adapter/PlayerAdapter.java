package fr.goui.riskgameofthroneshelperv2.adapter;

import android.content.Context;
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

    private LayoutInflater mLayoutInflater;

    private List<Player> mListOfPlayers;

    private PlayerModel mPlayerModel = PlayerModel.getInstance();

    public PlayerAdapter(Context context) {
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
            holder.playerView.setBackgroundColor(player.getColor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListOfPlayers.size() < 7) { // preventing picking color when max players
                        holder.playerView.setBackgroundColor(mPlayerModel.getNextAvailableColor(player));
                    }
                }
            });
        }
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
