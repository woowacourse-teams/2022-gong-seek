import { getDetailArticle } from '@/api/article';
import { getComments } from '@/api/comments';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';
import Detail from '../Detail';

const ErrorDetail = () => {
	const { id } = useParams<string>();

	// 게시글 조회
	const {
		data: articleData,
		isError: isArticleError,
		isSuccess: isArticleSuccess,
		isLoading: isArticleLoading,
		error: articleError,
	} = useQuery('detail-article', () => getDetailArticle(id));

	// 댓글 조회
	const {
		data: commentData,
		isError: isCommentError,
		isSuccess: isCommentSuccess,
		isLoading: isCommentLoading,
		error: commentError,
	} = useQuery('comments', getComments);

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

	return (
		<div>
			{isArticleSuccess && isCommentSuccess && (
				<Detail article={articleData} commentList={commentData} />
			)}
		</div>
	);
};

export default ErrorDetail;
