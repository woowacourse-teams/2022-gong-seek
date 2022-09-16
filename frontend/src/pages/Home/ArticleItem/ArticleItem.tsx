import { convertGithubAvartarUrlForResize } from '../../../utils/converter';
import { useNavigate } from 'react-router-dom';

import * as S from '@/pages/Home/ArticleItem/ArticleItem.styles';
import { CommonArticleType } from '@/types/articleResponse';

const ArticleItem = ({ article }: { article: CommonArticleType }) => {
	const navigate = useNavigate();
	const { id, title, author, commentCount, views, likeCount, category } = article;
	return (
		<>
			<S.Title onClick={() => navigate(`/articles/${category}/${id}`)}>{title}</S.Title>
			<S.ArticleInfo>
				<S.ProfileBox>
					<S.UserImg
						alt="유저의 프로필 이미지가 보여지는 곳 입니다 "
						src={convertGithubAvartarUrlForResize(author.avatarUrl)}
					/>
					<S.UserName>{author.name}</S.UserName>
				</S.ProfileBox>
				<S.SubInfoBox>
					<S.ViewsBox>
						<S.ViewsIcon>조회수</S.ViewsIcon>
						<S.ViewsCount aria-label="조회수가 표시되는 곳입니다">{views}</S.ViewsCount>
					</S.ViewsBox>
					<S.LikeBox>
						<S.LikeIcon />
						<S.LikeCount aria-label="좋아요 수가 표시되는 곳입니다">{likeCount}</S.LikeCount>
					</S.LikeBox>
					<S.CommentBox>
						<S.CommentIcon />
						<S.CommentCount aria-label="댓글의 개수가 표시되는 곳입니다">
							{commentCount}
						</S.CommentCount>
					</S.CommentBox>
				</S.SubInfoBox>
			</S.ArticleInfo>
		</>
	);
};

export default ArticleItem;
