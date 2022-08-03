import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/Discussion/VoteItem/VoteItem.styles';
import usePostVoteItem from '@/pages/Discussion/hooks/usePostVoteItem';
import { convertIdxToVoteColorKey } from '@/utils/converter';

export interface VoteItemProps {
	voteId: number;
	title: string;
	itemVotes: number;
	totalVotes: number;
	colorIdx: number;
	articleId: string;
	isExpired: boolean;
}

const VoteItem = ({
	voteId,
	title,
	itemVotes,
	totalVotes,
	colorIdx,
	articleId,
	isExpired,
}: VoteItemProps) => {
	const progressivePercent = Math.floor((itemVotes / totalVotes) * 100);
	const { onChangeRadio, isLoading } = usePostVoteItem(articleId);

	if (isLoading) return <Loading />;

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton
					type="radio"
					name={articleId}
					onChange={() => {
						onChangeRadio(articleId, voteId);
					}}
					disabled={isExpired}
				/>
				<S.Title>
					<p>{title}</p>
					<S.ItemVotes>{`(${itemVotes}표)`}</S.ItemVotes>
				</S.Title>
			</S.TitleBox>

			<S.ProgressiveBar>
				<S.ProgressiveBarContent
					percent={progressivePercent || 0}
					colorKey={convertIdxToVoteColorKey(colorIdx)}
				/>
			</S.ProgressiveBar>
		</S.Container>
	);
};

export default VoteItem;
