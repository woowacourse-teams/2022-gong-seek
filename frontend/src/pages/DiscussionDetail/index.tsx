import { useNavigate, useParams } from 'react-router-dom';

import Loading from '@/components/@common/Loading/Loading';
import Vote from '@/components/vote/Vote/Vote';
import VoteGenerateButton from '@/components/vote/VoteGenerateButton/VoteGenerateButton';
import useGetDetailArticle from '@/hooks/article/useGetDetailArticle';
import useGetDetailComment from '@/hooks/comment/useGetDetailComment';
import Detail from '@/pages/Detail';

const DiscussionDetail = () => {
	const { id } = useParams();
	const navigate = useNavigate();

	if (typeof id === 'undefined') {
		throw new Error('id 값을 받아오지 못했습니다');
	}

	const { data: articleData, isLoading: isArticleLoading } = useGetDetailArticle(id);
	const { data: commentData, isLoading: isCommentLoading } = useGetDetailComment(id);

	const onClickVoteGenerateButton = () => {
		navigate(`/votes/${id}`);
	};

	if (isCommentLoading || isArticleLoading) {
		return <Loading />;
	}

	return (
		<>
			{typeof articleData !== 'undefined' && typeof commentData !== 'undefined' && (
				<Detail
					article={articleData}
					commentList={commentData.comments}
					articleId={id}
					category="토론"
				>
					{articleData.hasVote ? (
						<Vote articleId={id} />
					) : articleData.isAuthor ? (
						<VoteGenerateButton onClick={onClickVoteGenerateButton}>투표 만들기</VoteGenerateButton>
					) : null}
				</Detail>
			)}
		</>
	);
};

export default DiscussionDetail;
