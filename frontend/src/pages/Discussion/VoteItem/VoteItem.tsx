import { theme } from '@/styles/Theme';
import { convertIdxToColorKey } from '@/utils/converter';
import * as S from './VoteItem.styles';
import { AxiosError } from 'axios';
import { useMutation } from 'react-query';
import { checkVoteItems } from '@/api/vote';

export interface VoteItemProps {
	title: string;
	itemVotes: number;
	totalVotes: number;
	idx: number;
	name: string;
}

const VoteItem = ({ title, itemVotes, totalVotes, idx, name }: VoteItemProps) => {
	const progressivePercent = (itemVotes / totalVotes) * 100;
	// const { isLoading, isError, mutate } = useMutation<unknown, AxiosError, string>(checkVoteItems);

	// if (isLoading) return <div>로딩중...</div>;

	// if (isError) return <div>에러..!</div>;

	return (
		<S.Container>
			<S.TitleBox>
				<S.RadioButton type="radio" name={name} />
				<S.Title>
					<p>{title}</p>
					<p
						css={{ color: theme.colors.PURPLE_400, fontSize: theme.size.SIZE_010 }}
					>{`(${itemVotes}표)`}</p>
				</S.Title>
			</S.TitleBox>

			<S.ProgressiveBar>
				<S.ProgressiveBarContent
					percent={progressivePercent}
					colorKey={convertIdxToColorKey(idx)}
				/>
			</S.ProgressiveBar>
		</S.Container>
	);
};

export default VoteItem;
