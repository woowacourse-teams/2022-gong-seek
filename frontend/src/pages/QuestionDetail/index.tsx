import { useParams } from 'react-router-dom';

import useGetDetailArticle from '@/hooks/queries/article/useGetDetailArticle';
import useGetDetailComment from '@/hooks/queries/comment/useGetDetailComment';
import Detail from '@/pages/Detail/index';

const QuestionDetail = () => {
	const { id } = useParams();

	if (typeof id === 'undefined') {
		throw new Error('id 값을 받아오지 못했습니다');
	}

	const { data: articleData, isLoading: isArticleLoading } = useGetDetailArticle(id);
	const { data: commentData, isLoading: isCommentLoading } = useGetDetailComment(id);

	if (isCommentLoading || isArticleLoading) {
		return <div>로딩중...</div>;
	}

	return (
		<div>
			{typeof articleData !== 'undefined' && typeof commentData !== 'undefined' && (
				<Detail
					article={articleData}
					commentList={commentData.comments}
					articleId={id}
					category="질문"
				/>
			)}
		</div>
	);
};

export default QuestionDetail;
