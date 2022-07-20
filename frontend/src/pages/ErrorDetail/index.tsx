import { getDetailArticle } from '@/api/article';
import { getComments } from '@/api/comments';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';
import Detail from '@/pages/Detail/index';
import { useSetRecoilState } from 'recoil';
import { articleState } from '@/store/articleState';
import { useEffect } from 'react';

const ErrorDetail = () => {
	const { id } = useParams<string>();
	const setTempArticle = useSetRecoilState(articleState);

	if (id === undefined) {
		throw new Error('id 값을 받아오지 못했습니다');
	}

	// 게시글 조회
	const {
		data: articleData,
		isError: isArticleError,
		isSuccess: isArticleSuccess,
		isLoading: isArticleLoading,
		error: articleError,
		remove,
	} = useQuery('detail-article', () => getDetailArticle(id));

	// 댓글 조회
	const {
		data: commentData,
		isError: isCommentError,
		isSuccess: isCommentSuccess,
		isLoading: isCommentLoading,
		error: commentError,
	} = useQuery('comments', () => getComments(id));

	if (isArticleLoading || isCommentLoading) {
		return <div>로딩중...</div>;
	}

	if (isArticleError || isCommentError) {
		return (
			<div>
				<>{articleError}</>
				<>{commentError}</>
			</div>
		);
	}

	useEffect(() => {
		remove();
	}, []);

	useEffect(() => {
		if (isArticleSuccess) {
			setTempArticle({ title: articleData.title, content: articleData.content });
		}
	}, [isArticleSuccess]);

	return (
		<div>
			{isArticleSuccess && isCommentSuccess && (
				<Detail article={articleData} commentList={commentData.comments} articleId={id} />
			)}
		</div>
	);
};

export default ErrorDetail;
