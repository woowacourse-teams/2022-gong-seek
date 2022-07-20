import { useState } from 'react';
import * as S from '@/components/common/ArticleItem/ArticleItem.styles';

const ArticleItem = () => {
	const [isHeartClick, setIsHeartClick] = useState(false);

	const onLikeButtonClick = () => {
		setIsHeartClick(!isHeartClick);
	};

	return (
		<S.Container>
			<S.ArticleItemTitle>
				<div>토론 글 제목이 들어가는 곳</div>
			</S.ArticleItemTitle>
			<S.ArticleInfoBox>
				<S.ArticleTimeStamp>5일전</S.ArticleTimeStamp>
				<S.Views>조회수 10</S.Views>
			</S.ArticleInfoBox>
			<S.Content>
				안녕하세요. 스밍입니다. 오늘 아침밥을 먹지 않아 배고프네요.
				fwfwfasfgennsnsnnnkdnvndvoeofjefjemoneonvnekofalccmlcoefnofwfnonwovojodwjodokfplcwplnveineinvie
			</S.Content>
			<S.FooterBox>
				<S.ProfileBox>
					<S.UserProfile src="https://avatars.githubusercontent.com/u/85891751?s=400&u=1d8557f04298a05f8a8bbceb9817b8a0089d63f8&v=4" />
					<div>스밍</div>
				</S.ProfileBox>
				<S.RightFooterBox>
					<S.HeartBox>
						{isHeartClick ? (
							<S.FillHeart onClick={onLikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div>10</div>
					</S.HeartBox>
				</S.RightFooterBox>
			</S.FooterBox>
		</S.Container>
	);
};

export default ArticleItem;
