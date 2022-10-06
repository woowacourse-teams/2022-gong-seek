import { PropsWithOptionalChildren } from 'gongseek-types';
import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import ArticleContent from '@/components/@common/ArticleContent/ArticleContent';
import Comment from '@/components/comment/Comment/Comment';
import CommentInputModal from '@/components/comment/CommentInputModal/CommentInputModal';
import { URL } from '@/constants/url';
import * as S from '@/pages/Detail/index.styles';
import { getUserIsLogin } from '@/store/userState';
import { ArticleType } from '@/types/articleResponse';
import { CommentType } from '@/types/commentResponse';

export interface DetailProps {
	article: ArticleType;
	commentList: CommentType[];
	articleId: string;
	category: string;
}

const Detail = ({
	children,
	article,
	commentList,
	articleId,
	category,
}: PropsWithOptionalChildren<DetailProps>) => {
	const [isCommentOpen, setIsCommentOpen] = useState(false);
	const isLogin = useRecoilValue(getUserIsLogin);
	const navigate = useNavigate();

	const { id } = useParams();
	if (typeof id === 'undefined') {
		throw new Error('글을 찾지 못하였습니다.');
	}

	const onClickCommentButton = () => {
		if (isLogin) {
			setIsCommentOpen(true);
			return;
		}

		if (window.confirm('로그인이 필요한 서비스입니다. 로그인 창으로 이동하시겠습니까?')) {
			navigate(URL.LOGIN);
		}
	};

	return (
		<S.Container>
			<ArticleContent
				article={article}
				author={article.author}
				category={category}
				articleId={articleId}
			/>
			{children}
			<S.CommentSection>
				<S.CommentInputBox>
					<S.CommentInput
						aria-label="댓글을 입력하는 창으로 이동하는 링크 입니다"
						onClick={onClickCommentButton}
						disabled={!isLogin}
					/>
					<S.CreateCommentButton
						aria-label="댓글을 입력하는 창으로 이동하는 링크입니다."
						onClick={onClickCommentButton}
						disabled={!isLogin}
					/>
				</S.CommentInputBox>

				<S.CommentHeader>
					<S.CommentTitle>댓글</S.CommentTitle>
					<S.CommentTotal>
						<S.CommentIcon />
						<div>{commentList.length || 0}개</div>
					</S.CommentTotal>
				</S.CommentHeader>
				{commentList && commentList.length > 0 ? (
					commentList.map((item) => (
						<Comment
							key={item.id}
							id={item.id}
							articleId={id}
							author={item.author}
							content={item.content}
							createdAt={item.createdAt}
							isAuthor={item.isAuthor}
						/>
					))
				) : (
					<div>첫 번째 댓글을 달아주세요!</div>
				)}
			</S.CommentSection>
			{isCommentOpen && (
				<>
					<S.DimmerContainer onClick={() => setIsCommentOpen(false)} />
					<CommentInputModal
						closeModal={() => setIsCommentOpen(false)}
						articleId={id}
						modalType="register"
						placeholder=""
					/>
				</>
			)}
		</S.Container>
	);
};

export default Detail;
