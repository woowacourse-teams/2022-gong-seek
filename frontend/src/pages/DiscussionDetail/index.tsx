import { Suspense } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Loading from '@/components/@common/Loading/Loading';
import Vote from '@/components/vote/Vote/Vote';
import VoteGenerateButton from '@/components/vote/VoteGenerateButton/VoteGenerateButton';
import useGetDetailArticle from '@/hooks/article/useGetDetailArticle';
import Detail from '@/pages/Detail';

const DiscussionDetail = () => {
	const { id } = useParams();
	const navigate = useNavigate();
	const { data } = useGetDetailArticle(id);

	if (typeof id === 'undefined') {
		throw new Error('id 값을 받아오지 못했습니다');
	}

	const handleClickVoteGenerateButton = () => {
		navigate(`/votes/${id}`);
	};

	return (
		<Suspense fallback={<Loading />}>
			{data && (
				<Detail article={data} articleId={id} category="토론">
					{data.hasVote ? (
						<Vote articleId={id} />
					) : data.isAuthor ? (
						<VoteGenerateButton onClick={handleClickVoteGenerateButton}>
							투표 만들기
						</VoteGenerateButton>
					) : null}
				</Detail>
			)}
		</Suspense>
	);
};

export default DiscussionDetail;
