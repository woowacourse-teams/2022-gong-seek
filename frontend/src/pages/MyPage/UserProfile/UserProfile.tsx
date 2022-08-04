import * as S from '@/pages/MyPage/UserProfile/UserProfile.styles';

export interface UserProfileProps {
	name: string;
	avatarUrl: string;
}

const UserProfile = ({ name, avatarUrl }: UserProfileProps) => (
	<S.Container>
		<S.UserProfile src={avatarUrl} />
		<S.UserName>{name}</S.UserName>
	</S.Container>
);

export default UserProfile;
