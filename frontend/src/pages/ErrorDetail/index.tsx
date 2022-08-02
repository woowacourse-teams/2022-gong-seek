import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import Detail from '@/pages/Detail/index';
import useGetDetailArticle from '@/hooks/useGetDetailArticle';
import useGetDetailComment from '@/hooks/useGetDetailComment';

const ErrorDetail = () => {
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
				<Detail article={articleData} commentList={commentData.comments} articleId={id} />
			)}
		</div>
	);
};

export default ErrorDetail;
