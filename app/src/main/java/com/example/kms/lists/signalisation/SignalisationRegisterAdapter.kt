import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.kms.databinding.SignalisationRegisterItemBinding
import com.example.kms.lists.signalisation.SignalisationItemComparator
import com.example.kms.lists.signalisation.SignalisationRegisterViewHolder
import com.example.kms.model.DTO.Audience

class SignalisationRegisterAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Audience, SignalisationRegisterViewHolder>(SignalisationItemComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SignalisationRegisterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SignalisationRegisterItemBinding.inflate(inflater, parent, false)
        val viewHolder = SignalisationRegisterViewHolder(binding, onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SignalisationRegisterViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }


}