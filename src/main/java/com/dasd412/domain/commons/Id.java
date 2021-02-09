package com.dasd412.domain.commons;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class Id<R,V> {

    /*
    reference에는 객체의 클래스 정보를, value에는 객체에 담고 싶은 정보(또는 프로퍼티)를 담는다.
    예를 들어 Id<User.class,user.getId()>형식으로 사용할 수 있다.
    일관성 있는 전달을 위하여 사용하였다.
     */

    private final Class<R>reference;

    private final V value;

    /*생성자를 private으로 잠그고 정적 팩토리 메소드 of()를 활용하였다.
     왜냐하면 이 클래스는 <R,V> 형인자 자료형 객체인데 생성자로 만들면 타이핑하기 힘들기 때문이다.
     */
    private Id(Class<R>reference, V value){
        this.reference=reference;
        this.value=value;
    }

    public static <R,V> Id<R,V> of(Class<R>reference, V value){
        //입력 값 검증은 모델 단에서 하는게 효율적이다.
        checkNotNull(reference,"Reference must be provided!");
        checkNotNull(value, "Value must be provided!");

        return new Id<>(reference,value);
    }

    public V value(){
        return value;
    }

    @Override
    public int hashCode() {//클래스 참조 값을 해싱한 값 리턴.
        return Objects.hash(reference);
    }

    @Override
    public boolean equals(Object obj) {
       if(this==obj)return true;//객체가 저장된 메모리 주소가 같다면, 같다.
       if(obj == null || getClass() != obj.getClass())return false;//null이거나 클래스가 다르면, 다르다.
       Id<?,?>id=(Id<?,?>)obj;
       return Objects.equals(reference,id.reference)&&Objects.equals(value,id.value);//클래스와 값이 같은지 확인.
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("reference",reference.getSimpleName())
                .append("value",value)
                .toString();
    }
}
